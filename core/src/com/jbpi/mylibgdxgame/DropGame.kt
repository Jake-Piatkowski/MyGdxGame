package com.jbpi.mylibgdxgame

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.jbpi.mylibgdxgame.screens.MainMenuScreen

class DropGame : Game() {

    lateinit var spriteBatch: SpriteBatch
    lateinit var bitmapFont: BitmapFont

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
}