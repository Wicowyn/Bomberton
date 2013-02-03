/*//////////////////////////////////////////////////////////////////////
	This file is part of Bomberton, an Bomberman-like.
	Copyright (C) 2012-2013  Nicolas Barranger <wicowyn@gmail.com>

    Bomberton is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Bomberton is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bomberton.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package collision;

import java.util.List;

import org.newdawn.slick.geom.Shape;


public interface Collidable {
    public Shape getNormalCollisionShape();
	public Shape getCollisionShape();
	public boolean isCollidingWith(Collidable collidable);
	
	public void addTouchHandle(TouchHandle handle);	
	public boolean removeTouchHandle(TouchHandle handle);	
	public void addTouchMarker(TouchMarker marker);	
	public boolean removeTouchMarker(TouchMarker marker);
	
	public List<TouchHandle> getAllTouchHandle();
	public List<TouchMarker> getAllTouchMarker();
	
	public void addCollidableListener(CollidableListener listener);
	public boolean removeCollidableListener(CollidableListener listener);
}
