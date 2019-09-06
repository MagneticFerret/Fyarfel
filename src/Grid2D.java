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

public class Grid2D {
	
	private static final int      DEFAULT_COLUMN_COUNT = 250;
	private static final int      DEFAULT_ROW_COUNT    = 250;
	private              Tile[][] grid2D;
	
	public Grid2D() {
		this( DEFAULT_ROW_COUNT, DEFAULT_COLUMN_COUNT );
	}
	
	public Grid2D( int rows, int cols ) {
		grid2D = new Tile[ rows ][ cols ];
	}
	
	public boolean isValidXCoord( int x ) {
		return x > -1 && x < width();
	}
	
	public boolean isValidYCoord( int y ) {
		return y > -1 && y < height();
	}
	
	public Tile getTile( int x, int y ) {
		return grid2D[ y ][ x ];
	}
	
	public void putTile( Tile tile, int x, int y ) {
		grid2D[ y ][ x ] = tile;
	}
	
	public int width() {
		return grid2D[ 0 ].length;
	}
	
	public int height() {
		return grid2D.length;
	}
	
	public void fill( Tile tile ) {
		for ( int row = 0; row < height(); row++ ) {
			for ( int col = 0; col < width(); col++ ) {
				grid2D[ row ][ col ] = tile;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for ( int row = 0; row < height(); row++ ) {
			for ( int col = 0; col < width(); col++ ) {
				stringBuilder.append( grid2D[ row ][ col ] );
			}
			stringBuilder.append( '\n' );
		}
		
		return stringBuilder.toString();
	}
	
}
