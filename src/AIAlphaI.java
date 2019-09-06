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

public class AIAlphaI extends AIBase implements Runnable {
	
	public AIAlphaI( Grid2D grid2D ) {
		super( grid2D );
	}
	
	@Override
	public void run() {
		running = true;
		
		while ( running ) {
			while ( willCollide( direction, 1 ) ) {
				direction = Direction.values()[ RNG.nextInt( Direction.values().length ) ];
			}
			
			move( direction, 1 );
			
			waitingToBeDrawn = true;
			while ( waitingToBeDrawn ) {
				try {
					Thread.sleep( 16 );
				} catch ( InterruptedException ie ) {
					System.out.println( "AlphaAI Thread interrupted!" );
					ie.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void stop() {
		running = false;
	}
	
	@Override
	protected void init() {
	}
	
	@Override
	public Tile getMapTile() {
		return TileRegistry.ALPHA_AI_TILE;
	}
	
}
