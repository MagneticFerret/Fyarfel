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
import java.util.HashMap;

public class TileRegistry {
	
	public static final DefaultTile DEFAULT_TILE  = new DefaultTile();
	public static final Forest      FOREST        = new Forest();
	public static final Road        ROAD          = new Road();
	public static final Sand        SAND          = new Sand();
	public static final Water       WATER         = new Water();
	public static final Fire        FIRE          = new Fire();
	public static final AlphaAITile ALPHA_AI_TILE = new AlphaAITile();
	public static final BetaAITile  BETA_AI_TILE  = new BetaAITile();
	public static final GammaAITile GAMMA_AI_TILE = new GammaAITile();
	
	private static final HashMap<String, Tile> TILE_REGISTRY = new HashMap<String, Tile>();
	
	private static final int DEFAULT_TILE_INDEX;
	
	static {
		TILE_REGISTRY.put( DEFAULT_TILE.toString(), DEFAULT_TILE );
		TILE_REGISTRY.put( FOREST.toString(), FOREST );
		TILE_REGISTRY.put( ROAD.toString(), ROAD );
		TILE_REGISTRY.put( SAND.toString(), SAND );
		TILE_REGISTRY.put( WATER.toString(), WATER );
		TILE_REGISTRY.put( FIRE.toString(), FIRE );
		
		DEFAULT_TILE_INDEX = new ArrayList<Tile>( TILE_REGISTRY.values() ).indexOf( DEFAULT_TILE );
	}
	
	public static Tile randomTile() {
		int randIndex = RNG.nextInt( TILE_REGISTRY.values().size() );
		while ( randIndex == DEFAULT_TILE_INDEX ) {
			randIndex = RNG.nextInt( TILE_REGISTRY.values().size() );
		}
		
		return TILE_REGISTRY.values().toArray( new Tile[ 0 ] )[ randIndex ];
	}
	
	public static Tile tileNumberResolution( int tileNum ) {
		if ( tileNum == DEFAULT_TILE_INDEX ) {
			return TILE_REGISTRY.values().toArray( new Tile[ 0 ] )[ tileNum + 1 ];
		}
		
		return TILE_REGISTRY.values().toArray( new Tile[ 0 ] )[ tileNum ];
	}
	
}
