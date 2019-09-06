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

import java.awt.*;
import java.util.HashMap;

public class ColorPalette {
	
	private static final HashMap<String, Color> COLOR_PALETTE;
	
	static {
		COLOR_PALETTE = new HashMap<String, Color>();
		COLOR_PALETTE.put( "default", new Color( 128, 0, 128 ) );
		COLOR_PALETTE.put( "sand", new Color( 194, 178, 128 ) );
		COLOR_PALETTE.put( "water", new Color( 28, 107, 160 ) );
		COLOR_PALETTE.put( "forest", new Color( 34, 139, 34 ) );
		COLOR_PALETTE.put( "road", new Color( 40, 43, 42 ) );
		COLOR_PALETTE.put( "fire", new Color( 178, 34, 34 ) );
		COLOR_PALETTE.put( "alphaAI", Color.ORANGE );
		COLOR_PALETTE.put( "betaAI", Color.BLUE );
		COLOR_PALETTE.put( "gammaAI", Color.MAGENTA );
	}
	
	public static Color color( String tile ) {
		return COLOR_PALETTE.containsKey( tile ) ? COLOR_PALETTE.get( tile ) : COLOR_PALETTE.get( "default" );
	}
	
}
