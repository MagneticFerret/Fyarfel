/*
 *  Copyright 2019 Maxwell C. Poarch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.concurrent.ArrayBlockingQueue;

public final class Turtle {
	
	private final String UID_GROUP_NAME;
	private final long   UID;
	
	private final Grid2D                                grid2D;
	private final ArrayBlockingQueue<TurtleInstruction> instructions;
	private final boolean                               continuousExec;
	private       Tile                                  holdingTile;
	private       Direction                             lastDirection;
	private       int                                   x;
	private       int                                   y;
	
	private Turtle() {
		UID_GROUP_NAME = "Turtle";
		UID            = -1L;
		grid2D         = null;
		instructions   = null;
		continuousExec = false;
	}
	
	public Turtle( Grid2D grid2D ) {
		this( grid2D, 0, 0, false );
	}
	
	public Turtle( Grid2D grid2D, boolean continuousExec ) {
		this( grid2D, 0, 0, continuousExec );
	}
	
	public Turtle( Grid2D grid2D, int x, int y, boolean continuousExec ) {
		this.grid2D         = grid2D;
		this.x              = x;
		this.y              = y;
		this.continuousExec = continuousExec;
		
		UID_GROUP_NAME = "Turtle";
		UID            = UIDGenerator.generateUID( UID_GROUP_NAME );
		
		this.instructions = new ArrayBlockingQueue<TurtleInstruction>( 1000000 );
		holdingTile       = null;
		lastDirection     = Direction.NO_DIRECTION;
		
		if ( continuousExec ) {
			continuousExecute();
		}
	}
	
	public void executeAllInstructions() {
		if ( !continuousExec ) {
			while ( !instructions.isEmpty() ) {
				executeInstruction( instructions.poll() );
			}
		}
	}
	
	public void addNewInstruction( Op op, Direction direction, Tile tile, int magnitude ) {
		try {
			instructions.put( new TurtleInstruction( op, direction, tile, magnitude, false ) );
		} catch ( InterruptedException ie ) {
			System.out.println( "Error adding instruction." );
			System.out.println( ie.getMessage() );
		}
	}
	
	public void addNewRandomInstruction() {
		try {
			instructions.put( new TurtleInstruction( true ) );
		} catch ( InterruptedException ie ) {
			System.out.println( "Error adding instruction." );
			System.out.println( ie.getMessage() );
		}
	}
	
	private void continuousExecute() {
		Thread continuousExecutionThread = new Thread( "TURTLE#" + UID ) {
			
			@Override
			public void run() {
				while ( true ) {
					if ( !instructions.isEmpty() ) {
						executeInstruction( instructions.poll() );
					} else {
						try {
							Thread.sleep( 10 );
						} catch ( InterruptedException ie ) {
							System.out.println( "Printing this shouldn't even be possible currently." );
						}
					}
				}
			}
			
		};
		continuousExecutionThread.start();
	}
	
	private void executeInstruction( TurtleInstruction instruction ) {
		Op        op        = instruction.op;
		Direction direction = processDirection( instruction.direction );
		Tile      tile      = instruction.tile;
		int       magnitude = instruction.magnitude;
		
		switch ( op ) {
			case MOVE:
				moveX( direction, magnitude );
				moveY( direction, magnitude );
				correctPosition();
				break;
			case PICK_UP:
				holdingTile = grid2D.getTile( x, y );
				break;
			case PUT_DOWN:
				if ( holdingTile != null ) {
					grid2D.putTile( holdingTile.clone(), x, y );
				} else if ( tile != null ) {
					grid2D.putTile( tile.clone(), x, y );
				}
				holdingTile = null;
				break;
			case NO_OP:
				break;
			default:
				break;
		}
		
		if ( direction != Direction.NO_DIRECTION
		     && direction != Direction.LEFT
		     && direction != Direction.RIGHT
		     && direction != Direction.SAME_DIRECTION ) {
			lastDirection = direction;
		}
	}
	
	private Direction processDirection( Direction direction ) {
		switch ( direction ) {
			case SAME_DIRECTION:
				return lastDirection;
			case LEFT:
				return left( direction );
			case RIGHT:
				return right( direction );
			default:
				return direction;
		}
	}
	
	private Direction left( Direction direction ) {
		switch ( lastDirection ) {
			case NORTH:
				return Direction.WEST;
			case NORTHEAST:
				return Direction.NORTHWEST;
			case EAST:
				return Direction.NORTH;
			case SOUTHEAST:
				return Direction.NORTHEAST;
			case SOUTH:
				return Direction.EAST;
			case SOUTHWEST:
				return Direction.SOUTHEAST;
			case WEST:
				return Direction.SOUTH;
			case NORTHWEST:
				return Direction.SOUTHWEST;
			default:
				return direction;
		}
	}
	
	private Direction right( Direction direction ) {
		switch ( lastDirection ) {
			case NORTH:
				return Direction.EAST;
			case NORTHEAST:
				return Direction.SOUTHEAST;
			case EAST:
				return Direction.SOUTH;
			case SOUTHEAST:
				return Direction.SOUTHWEST;
			case SOUTH:
				return Direction.WEST;
			case SOUTHWEST:
				return Direction.NORTHWEST;
			case WEST:
				return Direction.NORTH;
			case NORTHWEST:
				return Direction.NORTHEAST;
			default:
				return direction;
		}
	}
	
	private void correctPosition() {
		if ( x < 0 ) {
			x = 0;
		} else if ( x >= grid2D.width() ) {
			x = grid2D.width() - 1;
		}
		
		if ( y < 0 ) {
			y = 0;
		} else if ( y >= grid2D.height() ) {
			y = grid2D.height() - 1;
		}
	}
	
	private void moveX( Direction direction, int deltaX ) {
		switch ( direction ) {
			case NORTH:
			case SOUTH:
			case NO_DIRECTION:
				return;
			case NORTHEAST:
			case EAST:
			case SOUTHEAST:
				x += deltaX;
				return;
			case NORTHWEST:
			case WEST:
			case SOUTHWEST:
				x -= deltaX;
				return;
			case RANDOM_DIRECTION:
				x = RNG.nextInt( 2 ) == 1 ? x + deltaX : x - deltaX;
				return;
			default:
				return;
		}
	}
	
	private void moveY( Direction direction, int deltaY ) {
		switch ( direction ) {
			case EAST:
			case WEST:
			case NO_DIRECTION:
				return;
			case SOUTHEAST:
			case SOUTH:
			case SOUTHWEST:
				y += deltaY;
				return;
			case NORTH:
			case NORTHEAST:
			case NORTHWEST:
				y -= deltaY;
				return;
			case RANDOM_DIRECTION:
				y = RNG.nextInt( 2 ) == 1 ? y + deltaY : y - deltaY;
				return;
			default:
				return;
		}
	}
	
	public enum Op {
		MOVE,
		PICK_UP,
		PUT_DOWN,
		NO_OP
	}
	
	public enum Direction {
		NORTH,
		NORTHEAST,
		EAST,
		SOUTHEAST,
		SOUTH,
		SOUTHWEST,
		WEST,
		NORTHWEST,
		LEFT,
		RIGHT,
		SAME_DIRECTION,
		RANDOM_DIRECTION,
		NO_DIRECTION
	}
	
	private final class TurtleInstruction {
		
		final Op        op;
		final Direction direction;
		final Tile      tile;
		final int       magnitude;
		
		TurtleInstruction() {
			this( false );
		}
		
		TurtleInstruction( boolean randomize ) {
			this( Op.NO_OP, Direction.NO_DIRECTION, null, 0, randomize );
		}
		
		TurtleInstruction( Op op, Direction direction, Tile tile, int magnitude, boolean randomize ) {
			if ( randomize ) {
				this.op = Op.values()[ RNG.nextInt( Op.values().length ) ];
				//this.op = Op.values()[ RandomNumberGenerator.nextInt( 2 ) == 1 ? 0 : 2 ];
				
				if ( this.op == Op.MOVE ) {
					this.direction = Direction.values()[ RNG.nextInt( Direction.values().length ) ];
				} else {
					this.direction = Direction.NO_DIRECTION;
				}
				
				if ( this.op == Op.PUT_DOWN ) {
					this.tile = TileRegistry.randomTile();
				} else {
					this.tile = null;
				}
				
				if ( this.op == Op.MOVE ) {
					int range = ( grid2D.height() + grid2D.width() ) / 10;
					this.magnitude = RNG.nextInt( range );
				} else {
					this.magnitude = 0;
				}
			} else {
				this.op        = op;
				this.direction = direction;
				this.tile      = tile;
				this.magnitude = magnitude;
			}
		}
		
	}
	
}
