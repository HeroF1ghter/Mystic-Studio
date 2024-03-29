package menu.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import basic.classes.MysticButton;
import basic.classes.MysticStudioGame;
import level.classes.Level;
import level.classes.Village;
import player.classes.Player;
import ui.classes.Overlay;

public class MainMenu extends Menu {

	private MysticButton newButton;
	private MysticButton loadButton;
	private MysticButton optionsButton;
	private MysticButton creditsButton;
	private MysticButton exitButton;

	private Options options;
	private boolean optionsMenu = false;

	private Credits credits;
	private boolean creditsMenu = false;

	private SaveGameSelection saveGameSelection;
	private boolean saveGameSelectionMenu = false;

	private MysticStudioGame game;

	public MainMenu(MysticStudioGame game) throws SlickException, FileNotFoundException {
		backgroundImage = new Image("res/images/Main_Menu.png");
		menuButtons();
		this.game = game;
	}

	public void update(GameContainer container, int delta) throws SlickException, IOException {
		if (saveGameSelection != null) {
			saveGameSelectionMenu = saveGameSelection.getActive();
		}
		if (options != null) {
			optionsMenu = options.getActive();
		}
		if (credits != null) {
			creditsMenu = credits.getActive();
		}
		if (saveGameSelectionMenu) {
			saveGameSelection.update(container, delta);
		} else if (optionsMenu) {
			options.update(container, delta);
		} else if (creditsMenu) {
			credits.update(container, delta);
		} else {
			saveGameSelection = null;
			options = null;
			credits = null;
			System.gc();
			checkClick(container);
		}

	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawImage(backgroundImage, backgroundImagePosX, backgroundImagePosY);

		if (!optionsMenu && !creditsMenu && !saveGameSelectionMenu) {
			newButton.render(container, g);
			loadButton.render(container, g);
			optionsButton.render(container, g);
			creditsButton.render(container, g);
			exitButton.render(container, g);
		} else if (saveGameSelectionMenu) {
			saveGameSelection.render(container, g);
		} else if (optionsMenu) {
			options.render(container, g);
		} else if (creditsMenu) {
			credits.render(container, g);
		}
	}

	private void menuButtons() throws SlickException, FileNotFoundException {

		Image newButtonImage = new Image("res/images/New_Button.png");
		Shape newButtonShape = new Rectangle(760, 625, 400, 61);
		newButton = new MysticButton(760, 625, newButtonShape, newButtonImage);

		Image loadButtonImage = new Image("res/images/Load_Button.png");
		Shape loadButtonShape = new Rectangle(760, 700, 400, 61);
		loadButton = new MysticButton(760, 700, loadButtonShape, loadButtonImage);

		Image optionButtonImage = new Image("res/images/Options_Button.png");
		Shape optionButtonShape = new Rectangle(760, 775, 400, 61);
		optionsButton = new MysticButton(760, 775, optionButtonShape, optionButtonImage);

		Image creditsButtonImage = new Image("res/images/Credits_Button.png");
		Shape creditsButtonShape = new Rectangle(760, 850, 400, 61);
		creditsButton = new MysticButton(760, 850, creditsButtonShape, creditsButtonImage);

		Image exitButtonImage = new Image("res/images/Exit_Button.png");
		Shape exitButtonShape = new Rectangle(760, 925, 400, 61);
		exitButton = new MysticButton(760, 925, exitButtonShape, exitButtonImage);

	}

	private void checkClick(GameContainer container) throws SlickException, IOException {

		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (newButton.isClicked(container.getInput())) {
				newGame(container);
			} else if (loadButton.isClicked(container.getInput())) {
				loadGame();
			} else if (optionsButton.isClicked(container.getInput())) {
				gameOptions();
			} else if (creditsButton.isClicked(container.getInput())) {
				gameCredits();
			} else if (exitButton.isClicked(container.getInput())) {
				container.exit();
			}
		}
	}

	private void newGame(GameContainer container) throws FileNotFoundException, SlickException {
		saveGameSelectionMenu = true;
		saveGameSelection = new SaveGameSelection(game, false);
	}

	private void loadGame() throws FileNotFoundException, SlickException {
		saveGameSelectionMenu = true;
		saveGameSelection = new SaveGameSelection(game, true);
	}

	private void gameOptions() throws SlickException, IOException {
		optionsMenu = true;
		options = new Options(game);
	}

	private void gameCredits() throws SlickException, IOException {
		creditsMenu = true;
		credits = new Credits();
	}

	public void runGame(File saveFile) throws FileNotFoundException, SlickException {

		// set level
		Level level;
		level = new Village(game);
		game.setLevel(level, 1500, 600);

		// set player
		Player player = game.getPlayer();

		// set player stats for the current player
		game.getPlayer().setPlayerStats(game.getPlayer(), saveFile);
		switch (game.getPlayer().getCharacterClass()) {
		case "warrior":
			game.getPlayer().setMoveLeftImg1(new Image("res/images/Knight-left-walk-1.png"));
			game.getPlayer().setMoveLeftImg2(new Image("res/images/Knight-left-walk-2.png"));
			game.getPlayer().setMoveRightImg1(new Image("res/images/Knight-right-walk-1.png"));
			game.getPlayer().setMoveRightImg2(new Image("res/images/Knight-right-walk-2.png"));
			game.getPlayer().setSwordImage(new Image("res/images/Wood-Sword.png"));
			break;
		case "mage":
			game.getPlayer().setMoveLeftImg1(new Image("res/images/Mage-left-walk-1.png"));
			game.getPlayer().setMoveLeftImg2(new Image("res/images/Mage-left-walk-2.png"));
			game.getPlayer().setMoveRightImg1(new Image("res/images/Mage-right-walk-1.png"));
			game.getPlayer().setMoveRightImg2(new Image("res/images/Mage-right-walk-2.png"));
			game.getPlayer().setSwordImage(new Image("res/images/Magic-Wand.png"));
			break;
		case "ranger":
			game.getPlayer().setMoveLeftImg1(new Image("res/images/Knight-left-walk-1.png"));
			game.getPlayer().setMoveLeftImg2(new Image("res/images/Knight-left-walk-2.png"));
			game.getPlayer().setMoveRightImg1(new Image("res/images/Knight-right-walk-1.png"));
			game.getPlayer().setMoveRightImg2(new Image("res/images/Knight-right-walk-2.png"));
			game.getPlayer().setSwordImage(new Image("res/images/Wood-Sword.png"));
			break;

		default:
			break;
		}

		// set overlay
		game.setOverlay(new Overlay(player));

		// unset main menu
		game.setMainMenu(true);
	}

}
