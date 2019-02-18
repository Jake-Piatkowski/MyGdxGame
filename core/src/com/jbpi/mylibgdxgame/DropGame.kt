package com.jbpi.mylibgdxgame

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.jbpi.mylibgdxgame.screens.GameScreen
import com.jbpi.mylibgdxgame.screens.MainMenuScreen

class DropGame : Game() {

    lateinit var spriteBatch: SpriteBatch
    lateinit var bitmapFont: BitmapFont

    private val gameScreen: GameScreen by lazy { GameScreen(this) }

    override fun create() {
        spriteBatch = SpriteBatch()
        bitmapFont = BitmapFont()

        setScreen(MainMenuScreen(this))
    }

    override fun dispose() {
        spriteBatch.dispose()
        bitmapFont.dispose()

        gameScreen.dispose()

        super.dispose()
    }

    fun changeScreenToGame() {
        screen = gameScreen
    }
}