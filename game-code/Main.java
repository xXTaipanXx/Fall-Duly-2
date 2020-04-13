package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darkbrokengames.fallduly2.scenes.GameSceneManager;
import com.darkbrokengames.fallduly2.scenes.StartScene;

/**
 * КЛАСС ЗАПУСКА
*/
public class Main extends ApplicationAdapter implements Runnable {

	private SpriteBatch batch; // Упаковщик спрайтов.

	private GameSceneManager gsm; // Менеджер сцен.

	public Main(GameSceneManager gsm){
		this.gsm = gsm;

	}

	@Override
	public void create() {
		batch = new SpriteBatch(); // Создаем упаковщик текстур.
		GameData.loadData(); // Загружаем информацию о прошлом игровом процессе.
		Assets.UnitScale = Gdx.graphics.getWidth()/Assets.CAMERA_VIEW_SCALE;
		gsm.assets.firstLoad();
		gsm.push(new StartScene(gsm)); // Открываем сцену с основным меню.
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.125f, 0.125f, 0.125f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Очищаем экран.
		gsm.update(Gdx.graphics.getDeltaTime()); // Метод обновления с частотой deltaTime.
		gsm.render(batch); // Рисуем изображения.
	}


	/*
	Закрытие приложения.
	Уничтожаем все используемые ресурсы.
	*/
	@Override
	public void dispose() {
		batch.dispose(); // Уничтожаем упаковщик текстур.
		gsm.pop(); // Уничтожаем ресурсы, используемые в активной сцене.
	}

	@Override
	public void run() { }
}
