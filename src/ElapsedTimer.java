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

public class ElapsedTimer {
	
	// allows for easy reporting of elapsed time
	private long    startTime;
	private long    endTime;
	private boolean timing;
	
	public ElapsedTimer() {
		startTime = 0L;
		endTime   = Long.MIN_VALUE;
		timing    = false;
	}
	
	public void start() {
		if ( !timing ) {
			timing    = true;
			startTime = System.nanoTime();
		}
	}
	
	public void stop() {
		long temp = System.nanoTime();
		if ( timing ) {
			endTime = temp;
			timing  = false;
		}
	}
	
	public long elapsedTimeNanoSec() {
		return timing ? System.nanoTime() - startTime : endTime - startTime;
	}
	
	public long elapsedTimeMicroSec() {
		return elapsedTimeNanoSec() / 1000L;
	}
	
	public long elapsedTimeMilliSec() {
		return elapsedTimeNanoSec() / 1000000L;
	}
	
	public long elapsedTimeSec() {
		return elapsedTimeNanoSec() / 1000000000L;
	}
	
	public void reset() {
		startTime = 0;
		endTime   = Long.MIN_VALUE;
		timing    = false;
	}
	
	@Override
	public String toString() {
		return "s: "
		       + elapsedTimeSec()
		       + "\n"
		       + "ms: "
		       + elapsedTimeMilliSec()
		       + "\n"
		       + "us: "
		       + elapsedTimeMicroSec()
		       + "\n"
		       + "ns: "
		       + elapsedTimeNanoSec();
	}
	
}
