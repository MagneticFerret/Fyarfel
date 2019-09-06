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

import java.util.HashMap;

public final class LSystem implements MapGenerationTechnique {
	
	private final HashMap<Character, String> lSystemRules;
	private final String                     START;
	private final int                        DEFAULT_ITERATIONS;
	
	public LSystem() {
		lSystemRules = new HashMap<Character, String>();
		lSystemRules.put( 'A', "-BF+AFA+FB-" );
		lSystemRules.put( 'B', "+AF-BFB-FA+" );
		START              = "A";
		DEFAULT_ITERATIONS = 5;
	}
	
	@Override
	public void generateMap( Grid2D grid2D ) {
		grid2D.fill( TileRegistry.SAND );
		Turtle turtle = new Turtle( grid2D, 0, grid2D.height() - 1, true );
		turtle.addNewInstruction( Turtle.Op.NO_OP, Turtle.Direction.EAST, null, 0 );
		
		String system = generateNewSystem( 7 );
		for ( char symbol : system.toCharArray() ) {
			switch ( symbol ) {
				case 'F':
					turtle.addNewInstruction( Turtle.Op.PUT_DOWN, Turtle.Direction.NO_DIRECTION, TileRegistry.ROAD, 0 );
					turtle.addNewInstruction( Turtle.Op.MOVE, Turtle.Direction.SAME_DIRECTION, null, 1 );
					turtle.addNewInstruction( Turtle.Op.PUT_DOWN, Turtle.Direction.NO_DIRECTION, TileRegistry.ROAD, 0 );
					turtle.addNewInstruction( Turtle.Op.MOVE, Turtle.Direction.SAME_DIRECTION, null, 1 );
					turtle.addNewInstruction( Turtle.Op.PUT_DOWN, Turtle.Direction.NO_DIRECTION, TileRegistry.ROAD, 0 );
					break;
				case '-':
					turtle.addNewInstruction( Turtle.Op.NO_OP, Turtle.Direction.LEFT, null, 1 );
					break;
				case '+':
					turtle.addNewInstruction( Turtle.Op.NO_OP, Turtle.Direction.RIGHT, null, 1 );
					break;
				default:
					break;
			}
		}
	}
	
	private String generateNewSystem( int iterations ) {
		StringBuilder system = new StringBuilder( systemSize( iterations ) );
		system.append( START );
		for ( int cycle = 0; cycle < iterations; cycle++ ) {
			for ( int index = 0; index < system.length(); index++ ) {
				if ( lSystemRules.containsKey( system.charAt( index ) ) ) {
					String addition = lSystemRules.get( system.charAt( index ) );
					system.insert( index, addition );
					system.deleteCharAt( index + addition.length() );
					index += addition.length();
				}
			}
		}
		return system.toString();
	}
	
	private int systemSize( int iterations ) {
		return ( int )Math.pow( iterations, 3.0 );
	}
	
}
