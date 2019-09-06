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

import javax.swing.*;
import java.awt.*;

public class MapDisplay extends JFrame {
	
	private static final int DEFAULT_TILE_SIZE = 2;
	
	private final Grid2D   grid2D;
	private final Graphics display;
	private final int      tileSize;
	
	private MapDisplay() {
		grid2D   = null;
		display  = null;
		tileSize = -1;
	}
	
	public MapDisplay( Grid2D grid2D ) {
		this( grid2D, DEFAULT_TILE_SIZE, null );
	}
	
	public MapDisplay( Grid2D grid2D, int tileSize ) {
		this( grid2D, tileSize, null );
	}
	
	public MapDisplay( Grid2D grid2D, AIManager aiManager ) {
		this( grid2D, DEFAULT_TILE_SIZE, aiManager );
	}
	
	public MapDisplay( Grid2D grid2D, int tileSize, AIManager aiManager ) {
		super( "Fyarfel" );
		this.grid2D   = grid2D;
		this.tileSize = tileSize;
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setResizable( false );
		this.pack();
		this.setVisible( true );
		
		display = this.getGraphics();
		addShutdownGraphicsHook();
		
		try {
			Thread.sleep( 500 );
		} catch ( InterruptedException ie ) {
			System.out.println( "Printing this shouldn't even be possible currently." );
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( tileSize * grid2D.width() + this.getInsets().left + this.getInsets().right,
		                      tileSize * grid2D.height() + this.getInsets().top + this.getInsets().bottom );
	}
	
	//Cartesian to isometric:
	//isoX = cartX - cartY;
	//isoY = (cartX + cartY) / 2;
	
	//Isometric to Cartesian:
	
	//cartX = (2 * isoY + isoX) / 2;
	//cartY = (2 * isoY - isoX) / 2;
	
	public int cartToIsoX( int cartX, int cartY ) {
		return cartX - cartY;
	}
	
	public int cartToIsoY( int cartX, int cartY ) {
		return ( cartX + cartY ) / 2;
	}
	
	public int isoToCartX( int isoX, int isoY ) {
		return ( 2 * isoY + isoX ) / 2;
	}
	
	public int isoToCartY( int isoX, int isoY ) {
		return ( 2 * isoY - isoX ) / 2;
	}
	
	public void paintCartMap() {
		for ( int row = 0; row < grid2D.height(); row++ ) {
			for ( int col = 0; col < grid2D.width(); col++ ) {
				display.setColor( grid2D.getTile( col, row ).color() );
				display.fillRect( col * tileSize + this.getInsets().left,
				                  row * tileSize + this.getInsets().top,
				                  tileSize,
				                  tileSize );
			}
		}
		
		try {
			Thread.sleep( 50 );
		} catch ( InterruptedException ie ) {
			System.out.println( "Printing this shouldn't even be possible currently." );
		}
	}
	
	public void paintIsoMap() {
		for ( int row = 0; row < grid2D.height(); row++ ) {
			for ( int col = 0; col < grid2D.width(); col++ ) {
				display.setColor( grid2D.getTile( col, row ).color() );
				
				int x = col * DEFAULT_TILE_SIZE + this.getInsets().left + grid2D.width();
				int y = row * DEFAULT_TILE_SIZE + this.getInsets().top - grid2D.width();
				
				display.fillRect( cartToIsoX( x, y ) + tileSize * 100, cartToIsoY( x, y ), tileSize, tileSize );
			}
		}
		
		try {
			Thread.sleep( 50 );
		} catch ( InterruptedException ie ) {
			System.out.println( "Printing this shouldn't even be possible currently." );
		}
	}
	
	private void addShutdownGraphicsHook() {
		Runtime.getRuntime().addShutdownHook( new Thread( () -> display.dispose() ) );
		
	}
	
}
