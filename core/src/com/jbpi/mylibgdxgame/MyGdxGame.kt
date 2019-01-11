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
import com.badlogic.gdx.utils.TimeUtils

const val SCREEN_WIDTH = 800f
const val SCREEN_HEIGHT = 480f

const val RECTANGLE_BUCKET_WIDTH = 64f
const val RECTANGLE_BUCKET_HEIGHT = 64f
const val RECTANGLE_DROPLET_WIDTH = 64f
const val RECTANGLE_DROPLET_HEIGHT = 64f

class MyGdxGame : ApplicationAdapter() {

    lateinit var textureDrop: Texture
    lateinit var textureBucket: Texture
    lateinit var soundDrop: Sound
    lateinit var musicBackground: Music

    lateinit var rectangleBucket: Rectangle
    lateinit var raindrops: com.badlogic.gdx.utils.Array<Rectangle>

    lateinit var camera: OrthographicCamera

    lateinit var spriteBatch: SpriteBatch

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
        rectangleBucket.width = RECTANGLE_BUCKET_WIDTH
        rectangleBucket.height = RECTANGLE_BUCKET_HEIGHT
        rectangleBucket.x = SCREEN_WIDTH / 2f - RECTANGLE_BUCKET_WIDTH / 2f
        rectangleBucket.y = 20f

        camera = OrthographicCamera()
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)

        spriteBatch = SpriteBatch()

        raindrops = com.badlogic.gdx.utils.Array()
        spawnRaindrop()
    }

    override fun render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
        spriteBatch.draw(textureBucket, rectangleBucket.x, rectangleBucket.y)
        raindrops.forEach { spriteBatch.draw(textureDrop, it.x, it.y) }
        spriteBatch.end()

        camera.update()

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {

            spawnRaindrop()
        }

        val iterator = raindrops.iterator()

        while (iterator.hasNext()) {

            val raindrop = iterator.next()
            raindrop.y -= 200f * Gdx.graphics.deltaTime

            if (raindrop.y + RECTANGLE_DROPLET_HEIGHT < 0) {

                iterator.remove()
            }

            if (raindrop.overlaps(rectangleBucket)) {

                soundDrop.play()
                iterator.remove()
            }
        }

        handleTouchInput()
        handleKeyboardInput()

        if (rectangleBucket.x < 0f) {

            rectangleBucket.x = 0f
        }

        if (rectangleBucket.x > SCREEN_WIDTH - RECTANGLE_BUCKET_WIDTH) {

            rectangleBucket.x = SCREEN_WIDTH - RECTANGLE_BUCKET_WIDTH
        }
    }

    private fun handleTouchInput() {

        if (Gdx.input.isTouched) {

            convertedTouchPosition.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(convertedTouchPosition)
            rectangleBucket.x = convertedTouchPosition.x - RECTANGLE_BUCKET_WIDTH / 2f
        }
    }

    private fun handleKeyboardInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            rectangleBucket.x -= 200f * Gdx.input.deltaX
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            rectangleBucket.x += 200f * Gdx.input.deltaX
        }
    }

    override fun dispose() {

        textureDrop.dispose()
        textureBucket.dispose()
        soundDrop.dispose()
        musicBackground.dispose()
        spriteBatch.dispose()
    }

    private fun spawnRaindrop() {

        val rectangleRainDrop = Rectangle()
        rectangleRainDrop.x = MathUtils.random(0f, SCREEN_WIDTH - RECTANGLE_DROPLET_WIDTH)
        rectangleRainDrop.y = SCREEN_HEIGHT
        rectangleRainDrop.width = RECTANGLE_DROPLET_WIDTH
        rectangleRainDrop.height = RECTANGLE_DROPLET_HEIGHT

        raindrops.add(rectangleRainDrop)

        lastDropTime = TimeUtils.nanoTime()
    }
}
