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

package tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;


public class ResourceManager {
	private Logger log=LogManager.getLogger(getClass());
	private Map<String, Image> mapImage=new HashMap<String, Image>();
	private Map<String, Animation> mapAnimation=new HashMap<String, Animation>();
	private Map<String, SpriteSheet> mapSpriteSheet=new HashMap<String, SpriteSheet>();
	private Map<String, Sound> mapSound=new HashMap<String, Sound>();
	private Map<String, String> mapText=new HashMap<String, String>();
	
	public static final String UP_ANIMATION="_UP_ANIMATION";
	public static final String DOWN_ANIMATION="_DOWN_ANIMATION";
	public static final String RIGHT_ANIMATION="_RIGHT_ANIMATION";
	public static final String LEFT_ANIMATION="_LEFT_ANIMATION";
	
	public ResourceManager(){
		
	}
	
	public void load(String filePath) throws JDOMException, IOException, SlickException{
		SAXBuilder sax=new SAXBuilder();
		Document doc;
		
		doc=sax.build(new File(filePath));
		Element root=doc.getRootElement();
		List<Element> listElem=root.getChildren();
		
		for(Element elem : listElem){
			switch(elem.getName()){
			case "Image":
				loadImageE(elem);
				break;
			case "SpriteSheet":
				loadSpriteSheetE(elem);
				break;
			case "Animation":
				loadAnimationE(elem);
				break;
			case "Sound":
				loadSoundE(elem);
				break;
			case "Text":
				loadTextE(elem);
				break;
			default:
				this.log.warn("load: unknown type object -> "+elem.getName());
				continue;
			}
		}
	}
	
	protected void loadImageE(Element elem){
		String id=elem.getAttributeValue("id");
		
		switch(elem.getAttributeValue("src")){
		case "file":
			loadImage(id, elem.getText());
			break;
		case "spritesheet":
			try {
				loadImage(id,
						elem.getAttributeValue("idSpriteSheet"),
						elem.getAttribute("x").getIntValue(),
						elem.getAttribute("y").getIntValue());
			} catch (DataConversionException e) {
				this.log.error("could not load image: "+id+" because wrong format of attribute 'x' or 'y'");
			}
			break;
		default:
			this.log.error("could not load image: "+id+", unknown value: "+elem.getAttributeValue("src")+" of attribute 'src'");
		}
	}
	
	protected void loadSpriteSheetE(Element elem){
		try {
			loadSpriteSheet(
					elem.getAttributeValue("id"),
					elem.getText(),
					elem.getAttribute("th").getIntValue(),
					elem.getAttribute("tw").getIntValue());
		} catch (DataConversionException e) {
			this.log.error("could not load spriteSheet at: "+elem.getText()+" because wrong format of attribute 'tw' or 'th'");
		}
	}
	
	protected void loadAnimationE(Element elem){
		List<Element> listElem=elem.getChildren();
		List<AnimationData> listData=new ArrayList<AnimationData>();
		
		for(Element child : listElem){
			switch(child.getName()){
			case "Frame":
				AnimationData data=new AnimationData();
				try {
					data.duration=child.getAttribute("duration").getIntValue();
					data.x=child.getAttribute("x").getIntValue();
					data.y=child.getAttribute("y").getIntValue();
					
					listData.add(data);
				} catch (DataConversionException e) {
					this.log.error("could not load animation with id: "+elem.getAttributeValue("id")+" because wrong format of attribute of Frame");
				}
				break;
			default:
				this.log.warn("load: unknown type object -> "+elem.getName());
				continue;	
			}
		}
		
		loadAnimation(elem.getAttributeValue("id"), elem.getAttributeValue("idSpriteSheet"), listData.toArray(new AnimationData[0]));
	}
	
	protected void loadSoundE(Element elem){
		loadSound(elem.getAttributeValue("id"), elem.getText());
	}
	
	protected void loadTextE(Element elem){
		loadText(elem.getAttributeValue("id"), elem.getText());
	}
	
	public void loadImage(String id, String filePath){
		try {
			Image img=new Image(filePath);
			this.mapImage.put(id, img);
		} catch (SlickException e) {
			this.log.error("could not load image at: "+filePath);
		}
		
	}
	
	public void loadImage(String id, String idSpriteSheet, int x, int y){
		SpriteSheet sheet=getSpriteSheet(idSpriteSheet);
		if(sheet==null){
			this.log.error("could not load image: "+id+" because the spriteSheet: "+idSpriteSheet+" is missing");
			return;
		}
		
		this.mapImage.put(id, sheet.getSubImage(x, y));
	}
	
	public void loadSpriteSheet(String id, String filePath, int tw, int th){
		try {
			SpriteSheet sheet=new SpriteSheet(new Image(filePath), tw, th);
			this.mapSpriteSheet.put(id, sheet);
		} catch (SlickException e) {
			this.log.error("could not load spriteSheet at: "+filePath);
		}
	}
	
	public void loadAnimation(String id, String idSpriteSheet, AnimationData... datas){
		SpriteSheet sheet=getSpriteSheet(idSpriteSheet);
		if(sheet==null){
			this.log.error("could not load animation: "+id+" because the spriteSheet: "+idSpriteSheet+" is missing");
			return;
		}

		Animation anim=new Animation(true);
		for(AnimationData data : datas){
			anim.addFrame(sheet.getSubImage(data.x,  data.y), data.duration);
		}

		this.mapAnimation.put(id, anim);
	}
	
	public void loadSound(String id, String filePath){
		try {
			Sound sound=new Sound(filePath);
			this.mapSound.put(id, sound);
		} catch (SlickException e) {
			this.log.error("could not load sound at: "+filePath);
		}
	}
	
	public void loadText(String id, String text){
		this.mapText.put(id, text);
	}
	
	public Image getImage(String id){
		Image old=this.mapImage.get(id);
		
		return old==null ? null : this.mapImage.get(id).copy();
	}
	
	public SpriteSheet getSpriteSheet(String id){
		SpriteSheet old=this.mapSpriteSheet.get(id);
		
		return old==null ? null : new SpriteSheet(old.copy(), old.getWidth()/old.getHorizontalCount(), old.getHeight()/old.getVerticalCount());
	}
	
	public Animation getAnimation(String id){
		Animation old=this.mapAnimation.get(id);
		
		return old==null ? null : old.copy();
	}
	
	public Renderable getRenderable(String id){
		Renderable render=getAnimation(id);
		
		return render==null ? getImage(id) : render;
	}
	
	public Sound getSound(String id){
		return this.mapSound.get(id);
	}
	
	public String getText(String id){
		return this.mapText.get(id);
	}
	
	public class AnimationData{
		public int x;
		public int y;
		public int duration;
	}
}