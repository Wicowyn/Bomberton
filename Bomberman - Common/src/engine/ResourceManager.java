package engine;

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
		loadImage(elem.getAttributeValue("id"), elem.getText());
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
		
		loadAnimation(elem.getAttributeValue("id"), elem.getAttributeValue("idSpriteSheet"), (AnimationData[]) listData.toArray());
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
			this.log.error("could not load animation because the spriteSheet: "+idSpriteSheet+" is missing");
			return;
		}
		
		Animation anim=new Animation(sheet, 100);
		for(AnimationData data : datas){
			anim.addFrame(data.duration, data.x, data.y);
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
		return this.mapImage.get(id);
	}
	
	public SpriteSheet getSpriteSheet(String id){
		return this.mapSpriteSheet.get(id);
	}
	
	public Animation getAnimation(String id){
		return this.mapAnimation.get(id);
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