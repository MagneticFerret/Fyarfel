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

public class SandMap implements MapGenerationTechnique {
	
	@Override
	public void generateMap( Grid2D grid2D ) {
		for ( int row = 0; row < grid2D.height(); row++ ) {
			for ( int col = 0; col < grid2D.width(); col++ ) {
				if ( RNG.nextInt( 100 ) > 10 ) {
					grid2D.putTile( TileRegistry.SAND, col, row );
				} else {
					grid2D.putTile( TileRegistry.FOREST, col, row );
				}
			}
		}
		
		for ( int col = 0; col < grid2D.width(); col++ ) {
			grid2D.putTile( TileRegistry.FOREST, col, 0 );
		}
		{
			int mapHeight = grid2D.height() - 1;
			for ( int col = 0; col < grid2D.width(); col++ ) {
				grid2D.putTile( TileRegistry.FOREST, col, mapHeight );
			}
		}
		int mapWidth = grid2D.width() - 1;
		for ( int row = 0; row < grid2D.height(); row++ ) {
			grid2D.putTile( TileRegistry.FOREST, 0, row );
			grid2D.putTile( TileRegistry.FOREST, mapWidth, row );
		}
	}
	
}
