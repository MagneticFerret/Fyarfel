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

public final class MapGenerator {
	
	private final Grid2D grid2D;
	
	private MapGenerator() {
		this.grid2D = null;
	}
	
	public MapGenerator( Grid2D grid2D ) {
		this.grid2D = grid2D;
	}
	
	public void generateMap( String name ) {
		MapGenTechniqueRegistry.getTechnique( name ).generateMap( grid2D );
	}
	
	//	public void bubbleSort() {
	//		boolean isSorted = false;
	//
	//		for (int i = 0; i < grid2D.length; i++) {
	//			//drawmap();
	//			while (!isSorted) {
	//				isSorted = true;
	//
	//				for (int j = 0; j < grid2D[i].length - 1; j++) {
	//					if (grid2D[i][j] < grid2D[i][j + 1]) {
	//						int k = grid2D[i][j];
	//						grid2D[i][j] = grid2D[i][j + 1];
	//						grid2D[i][j + 1] = k;
	//						isSorted = false;
	//					}
	//				}
	//			}
	//		}
	//	}
}
