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

import java.util.ArrayList;

public abstract class AIBase implements Runnable {
	
	private static final ArrayList<Tile> invalidTiles;
	
	static {
		invalidTiles = new ArrayList<Tile>();
		invalidTiles.add( TileRegistry.DEFAULT_TILE );
		invalidTiles.add( TileRegistry.FIRE );
		invalidTiles.add( TileRegistry.FOREST );
		invalidTiles.add( TileRegistry.WATER );
	}
	
	protected final Grid2D    grid2D;
	protected       Direction direction;
	protected       int       posX, posY;
	protected boolean running, waitingToBeDrawn;
	
	private AIBase() {
		this.grid2D           = null;
		this.direction        = null;
		this.posX             = 0;
		this.posY             = 0;
		this.running          = false;
		this.waitingToBeDrawn = false;
	}
	
	protected AIBase( Grid2D grid2D ) {
		this( grid2D, 1, 1 );
	}
	
	protected AIBase( Grid2D grid2D, int initPosX, int initPosY ) {
		this.grid2D           = grid2D;
		this.direction        = Direction.EAST;
		this.posX             = initPosX;
		this.posY             = initPosY;
		this.running          = false;
		this.waitingToBeDrawn = false;
		correctPosition();
		init();
	}
	
	@Override
	public abstract void run();
	
	public abstract void stop();
	
	protected abstract void init();
	
	public abstract Tile getMapTile();
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void doNotWaitForDraw() {
		waitingToBeDrawn = false;
	}
	
	protected void move( Direction direction, int magnitude ) {
		posX = newXCoord( direction, posX, magnitude );
		posY = newYCoord( direction, posY, magnitude );
		correctPosition();
	}
	
	protected boolean willCollide( Direction direction, int magnitude ) {
		int pathX = posX;
		int pathY = posY;
		while ( magnitude != 0 ) {
			pathX = newXCoord( direction, pathX, 1 );
			pathY = newYCoord( direction, pathY, 1 );
			
			pathX = pathX < 0 ? 0 : pathX;
			pathX = pathX >= grid2D.width() ? grid2D.width() - 1 : pathX;
			pathY = pathY < 0 ? 0 : pathY;
			pathY = pathY >= grid2D.height() ? grid2D.height() - 1 : pathY;
			
			if ( invalidTiles.contains( grid2D.getTile( pathX, pathY ) ) ) {
				return true;
			}
			
			magnitude--;
		}
		return false;
	}
	
	private void correctPosition() {
		if ( posX < 0 ) {
			posX = 0;
		} else if ( posX >= grid2D.width() ) {
			posX = grid2D.width() - 1;
		}
		
		if ( posY < 0 ) {
			posY = 0;
		} else if ( posY >= grid2D.height() ) {
			posY = grid2D.height() - 1;
		}
	}
	
	private int newXCoord( Direction direction, int currentX, int magnitude ) {
		switch ( direction ) {
			case WEST:
			case NORTHWEST:
			case SOUTHWEST:
				return currentX - magnitude;
			case EAST:
			case NORTHEAST:
			case SOUTHEAST:
				return currentX + magnitude;
			case NORTH:
			case SOUTH:
				return currentX;
			default:
				return currentX;
		}
	}
	
	private int newYCoord( Direction direction, int currentY, int magnitude ) {
		switch ( direction ) {
			case NORTH:
			case NORTHWEST:
			case NORTHEAST:
				return currentY - magnitude;
			case WEST:
			case EAST:
				return currentY;
			case SOUTH:
			case SOUTHWEST:
			case SOUTHEAST:
				return currentY + magnitude;
			default:
				return currentY;
		}
	}
	
	public enum Direction {
		NORTH,
		NORTHEAST,
		EAST,
		SOUTHEAST,
		SOUTH,
		SOUTHWEST,
		WEST,
		NORTHWEST
	}
	
}
