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

public class MarkovChain implements MapGenerationTechnique {
	
	private static final int DEFAULT_STATES = 6;
	
	private final double[][] markovMatrix;
	
	public MarkovChain() {
		this( DEFAULT_STATES );
	}
	
	public MarkovChain( int markovMatrixLength ) {
		markovMatrix = new double[ markovMatrixLength ][ markovMatrixLength ];
		initMarkovMatrix( markovMatrix );
	}
	
	@Override
	public void generateMap( Grid2D grid2D ) {
		int state = 0;
		for ( int row = 0; row < grid2D.height(); row++ ) {
			for ( int col = 0; col < grid2D.width(); col++ ) {
				double draw  = RNG.nextDouble();
				double total = 0.0;
				for ( int index = 0; index < markovMatrix.length; index++ ) {
					if ( draw > total && draw < total + markovMatrix[ state ][ index ] ) {
						state = index;
						grid2D.putTile( TileRegistry.tileNumberResolution( index ), col, row );
						break;
					} else {
						total += markovMatrix[ state ][ index ];
					}
				}
			}
		}
	}
	
	private boolean isValidMarkovMatrix( double[][] markovMatrix ) {
		for ( int row = 0; row < markovMatrix.length; row++ ) {
			double total = 0.0;
			for ( int col = 0; col < markovMatrix.length; col++ ) {
				total += markovMatrix[ row ][ col ];
				
				if ( total > 1.0 ) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void equalMarkovMatrix( double[][] markovMatrix ) {
		double equal = 1.0 / markovMatrix.length;
		for ( int row = 0; row < markovMatrix.length; row++ ) {
			for ( int col = 0; col < markovMatrix.length; col++ ) {
				markovMatrix[ row ][ col ] = equal;
			}
		}
	}
	
	private void skewedMarkovMatrix( double[][] markovMatrix ) {
		double average = 1.0 / markovMatrix.length;
		for ( int row = 0; row < markovMatrix.length; row++ ) {
			double total = 0;
			for ( int col = 0; col < markovMatrix.length; col++ ) {
				if ( row == 0 ) {
					markovMatrix[ row ][ col ] = 1.0;
				} else {
					markovMatrix[ row ][ col ] = 0.0;
				}
			}
		}
	}
	
	private void initMarkovMatrix( double[][] markovMatrix ) {
		for ( int row = 0; row < markovMatrix.length; row++ ) {
			double total = 0;
			for ( int col = 0; col < markovMatrix.length; col++ ) {
				double nextProbability = RNG.nextDouble() / 2.0;
				if ( total + nextProbability > 1.0 ) {
					nextProbability = 1.0 - total;
				}
				total += nextProbability;
				
				markovMatrix[ row ][ col ] = nextProbability;
			}
			
			if ( 1.0 - total > 0.0 ) {
				markovMatrix[ row ][ markovMatrix.length - 1 ] += 1.0 - total;
			}
		}
	}
	
}
