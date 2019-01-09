package com.jbpi.mylibgdxgame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3

class MyGdxGame : ApplicationAdapter() {

    lateinit var textureDrop: Texture
    lateinit var textureBucket: Texture
    lateinit var soundDrop: Sound
    lateinit var musicBackground: Music

    lateinit var rectangleBucket: Rectangle

    lateinit var camera: OrthographicCamera
    lateinit var spriteBatch: SpriteBatch

    lateinit var drops: Array<Rectangle>

    var lastDropTime: Long = 0

    var convertedTouchPosition: Vector3 = Vector3()

    override fun create() {

        textureDrop = Texture(Gdx.files.internal("droplet.png"))
        textureBucket = Texture(Gdx.files.internal("bucket.png"))
        soundDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
        musicBackground = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))

        musicBackground.isLooping = true
//        musicBackground.play()

        rectangleBucket = Rectangle()
        rectangleBucket.width = 64f
        rectangleBucket.height = 64f
        rectangleBucket.x = 800f / 2f - 64f / 2f
        rectangleBucket.y = 20f

        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)

        spriteBatch = SpriteBatch()
    }

    override fun render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
        spriteBatch.draw(textureBucket, rectangleBucket.x, rectangleBucket.y)
        spriteBatch.end()

        camera.update()

        if (Gdx.input.isTouched) {

            convertedTouchPosition.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(convertedTouchPosition)
            rectangleBucket.x = convertedTouchPosition.x - 64f / 2f
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            rectangleBucket.x -= 200f * Gdx.input.deltaX
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            rectangleBucket.x += 200f * Gdx.input.deltaX
        }

        if (rectangleBucket.x < 0f) {

            rectangleBucket.x = 0f
        }

        if (rectangleBucket.x > 800f - 64f) {
            rectangleBucket.x = 800f - 64f
        }
    }

//    override fun dispose() {
//        batch.dispose()
//        img.dispose()
//    }

    private fun spawnRaindrop() {
        val rectangleRainDrop = Rectangle()
        rectangleRainDrop.x = MathUtils.random(0f, 800f - 64f)
        rectangleRainDrop.y = 480f

        rectangleRainDrop.width = 64f
        rectangleRainDrop.height = 64f

    }
}
