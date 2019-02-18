package com.jbpi.mylibgdxgame

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.jbpi.mylibgdxgame.screens.GameScreen
import com.jbpi.mylibgdxgame.screens.MainMenuScreen

class DropGame : Game() {

    lateinit var spriteBatch: SpriteBatch
    lateinit var bitmapFont: BitmapFont

    private lateinit var gameScreen: GameScreen

    override fun create() {
        spriteBatch = SpriteBatch()
        bitmapFont = BitmapFont()

        setScreen(MainMenuScreen(this))
    }

    override fun dispose() {
        spriteBatch.dispose()
        bitmapFont.dispose()

        super.dispose()
    }

    fun changeScreenToGame() {
        gameScreen = GameScreen(this)
        screen = gameScreen
    }
}