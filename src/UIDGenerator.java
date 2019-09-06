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
import java.util.HashSet;

public final class UIDGenerator {
	
	private static final HashMap<String, Group> GROUPS = new HashMap<String, Group>();
	
	static {
		Group defaultGroup = new Group();
		GROUPS.put( "DEFAULT", defaultGroup );
		GROUPS.put( "", defaultGroup );
	}
	
	public static long generateUID() {
		return generateUID( "DEFAULT" );
	}
	
	public static synchronized long generateUID( String groupName ) {
		long UID = RNG.nextLong();
		while ( UID < 0L || isUIDRegistered( UID ) ) {
			UID = RNG.nextLong();
		}
		
		Group group = GROUPS.get( groupName );
		if ( group == null ) {
			group = new Group();
			GROUPS.put( groupName, group );
		}
		group.UIDs.add( UID );
		
		return UID;
	}
	
	public static boolean isUIDRegistered( long UID ) {
		for ( Group group : GROUPS.values() ) {
			if ( group.UIDs.contains( UID ) ) {
				return true;
			}
		}
		return false;
	}
	
	private static class Group {
		
		final HashSet<Long> UIDs;
		
		Group() {
			UIDs = new HashSet<Long>();
		}
		
	}
	
}
