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

public class Experimental implements MapGenerationTechnique {
	
	@Override
	public void generateMap( Grid2D grid2D ) {
		grid2D.fill( TileRegistry.FOREST );
		for ( int row = 0; row < grid2D.height(); row++ ) {
			for ( int col = 0; col < grid2D.width(); col++ ) {
				if ( row % ( col + 1 ) == 0 ) {
					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				}
				if ( col % ( row + 1 ) == 0 ) {
					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				}
				
				// fills the top right triangle
				//				if ( row % ( col + 1 ) == row ) {
				//					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				//				}
				
				// fills the bottom left triangle
				//				if ( col % ( row + 1 ) == col ) {
				//					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				//				}
				
				// fills in the inside of a parabola ( y = x^2 )
				//				if ( ( int )Math.sqrt( row * row + col * col ) == row ) {
				//					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				//				}
				
				// fills in the inside of a parabola ( x = y^2 )
				//				if ( ( int )Math.sqrt( row * row + col * col ) == col ) {
				//					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				//				}
				
				// plots points of a parabola ( y = x^2 )
				if ( col * col == row ) {
					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				}
				
				// plots points of a parabola ( x = y^2 )
				if ( row * row == col ) {
					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				}
				
				if ( ( int )Math.sqrt( row * row + col * col ) == row * col ) {
					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				}
				
				int random = RNG.nextInt( 10 );
				if ( ( row - grid2D.height() / 2 ) * ( row - grid2D.height() / 2 ) + ( col - grid2D.width() / 2 ) * (
						col
						- grid2D.width() / 2 ) == 100 ) {
					grid2D.putTile( TileRegistry.ROAD.clone(), col, row );
				}
			}
		}
	}
	
}
