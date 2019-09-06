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

public class Fyarfel {
	
	private Grid2D       grid2D;
	private MapGenerator mapGenerator;
	private ElapsedTimer timer;
	private MapDisplay   mapDisplay;
	
	private String genTime     = "";
	private String printTime   = "";
	private String displayTime = "";
	
	public Fyarfel() {
		grid2D       = new Grid2D( 300, 300 );
		mapGenerator = new MapGenerator( grid2D );
		timer        = new ElapsedTimer();
		mapDisplay   = new MapDisplay( grid2D );
	}
	
	public void initilize() {
		timer.start();
		mapGenerator.generateMap( "LSystem" );
		timer.stop();
		genTime = timer.toString();
		
		timer.start();
		System.out.println( grid2D );
		timer.stop();
		printTime = timer.toString();
		
		timer.start();
		mapDisplay.paintCartMap();
		timer.stop();
		displayTime = timer.toString();
		
		printTimes();
		
		while ( true ) {
			update();
			draw();
		}
	}
	
	public void update() {
	}
	
	public void draw() {
		mapDisplay.paintCartMap();
	}
	
	public void printTimes() {
		System.out.println( "Generate" );
		System.out.println( genTime );
		System.out.println( "Print" );
		System.out.println( printTime );
		System.out.println( "Display" );
		System.out.println( displayTime );
	}
	
}
