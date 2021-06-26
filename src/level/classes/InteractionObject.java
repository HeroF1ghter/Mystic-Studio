package level.classes;

import java.io.FileNotFoundException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import audio.classes.GameSound;
import basic.classes.GraphicObject;
import basic.classes.MysticStudioGame;
import menu.classes.PickLevel;

public class InteractionObject extends GraphicObject {
	
	private MysticStudioGame game;
	private String interaction;

	public InteractionObject(int xPos, int yPos, Shape hitbox, Image image, MysticStudioGame game, String interaction) {
		super(xPos, yPos, hitbox, image);
		this.game = game;
		this.interaction = interaction;
	}
	
	public void executeInteraction() throws FileNotFoundException, SlickException {
		// set level
		Level level;
		switch (interaction) {
		case "TestLevel":
			level = new TestLevel1(game);
			game.setLevel(level, 1600, 300);			
			break;
		case "BasementLevel":
			if (game.getPlayer().getGameProgress() < 1) {
				game.getPlayer().setGameProgress(1);
			}
			level = new Village(game);
			game.setLevel(level, 1500, 600);
			break;
		case "CaveLevel":
			if (game.getPlayer().getGameProgress() < 2) {
				game.getPlayer().setGameProgress(2);
			}
			level = new Village(game);
			game.setLevel(level, 1500, 600);
			break;
		case "Village":
			level = new Village(game);
			game.setLevel(level, 1500, 600);			
			break;
		case "SelectLevel":
			PickLevel pickLevel = new PickLevel(game);	
			game.setPickLevel(pickLevel);
			break;
		case "villager":
			GameSound villagerSound = new GameSound("res/voice-files/goodDay.wav");
			villagerSound.playSound();
			break;
		case "merchant":
			GameSound merchantSound = new GameSound("res/voice-files/merchant.wav");
			merchantSound.playSound();
			break;
		case "Quest01":
			GameSound quest01Sound = new GameSound("res/voice-files/quest01.wav");
			quest01Sound.playSound();
			break;
		default:
			break;
		}
	}
}
