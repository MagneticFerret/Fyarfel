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

import java.util.Random;

public final class RNG {
	
	private static final Random RNG = new Random();
	
	public static double nextDouble() {
		return RNG.nextDouble();
	}
	
	public static int nextInt() {
		return RNG.nextInt();
	}
	
	/*
	 * @param end end of interval
	 * @return returns an integer in the interval [0 (inclusive), end (exclusive)]
	 */
	public static int nextInt( int end ) {
		return RNG.nextInt( end );
	}
	
	/*
	 * @param start start of interval (inclusive)
	 * @param end   end of interval (exclusive)
	 * @return a number between start and end
	 */
	public static int nextInt( int start, int end ) {
		return start + RNG.nextInt( end - start );
	}
	
	public static long nextLong() {
		return RNG.nextLong();
	}
	
}
