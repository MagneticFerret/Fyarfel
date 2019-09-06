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

public final class MapGenTechniqueRegistry {
	
	private static final HashMap<String, MapGenerationTechnique> TECHNIQUE_REGISTRY;
	
	static {
		TECHNIQUE_REGISTRY = new HashMap<String, MapGenerationTechnique>( 10 );
		TECHNIQUE_REGISTRY.put( "Default", new DefaultGenerationTechnique() );
		TECHNIQUE_REGISTRY.put( "LSystem", new LSystem() );
		TECHNIQUE_REGISTRY.put( "Lines", new Lines() );
		TECHNIQUE_REGISTRY.put( "Experimental", new Experimental() );
		TECHNIQUE_REGISTRY.put( "MarkovChain", new MarkovChain() );
		TECHNIQUE_REGISTRY.put( "SandMap", new SandMap() );
		TECHNIQUE_REGISTRY.put( "Kruskal", new Kruskal() );
	}
	
	public static MapGenerationTechnique getTechnique( String name ) {
		return TECHNIQUE_REGISTRY.get( name ) != null
		       ? TECHNIQUE_REGISTRY.get( name )
		       : TECHNIQUE_REGISTRY.get( "Default" );
	}
	
	public static void registerTechnique( String name, MapGenerationTechnique technique ) {
		TECHNIQUE_REGISTRY.put( name, technique );
	}
	
}
