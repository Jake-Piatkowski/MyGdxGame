package com.jbpi.mylibgdxgame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.TimeUtils
import com.jbpi.mylibgdxgame.DropGame

const val SCREEN_WIDTH = 800f
const val SCREEN_HEIGHT = 480f

const val RECTANGLE_BUCKET_WIDTH = 64f
const val RECTANGLE_BUCKET_HEIGHT = 64f
const val RECTANGLE_DROPLET_WIDTH = 64f
const val RECTANGLE_DROPLET_HEIGHT = 64f

const val INTERVAL_BETWEEN_DROPLETS = 1000000000

class GameScreen (private val game: DropGame) : Screen {

    private var textureDroplet: Texture = Texture(Gdx.files.internal("droplet.png"))
    private var textureBucket: Texture = Texture(Gdx.files.internal("bucket.png"))
    private var soundDroplet: Sound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
    private var musicBackground: Music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))

    private val rectangleBucket = Rectangle()
    private val rectanglesDroplets = com.badlogic.gdx.utils.Array<Rectangle>()

    private val camera = OrthographicCamera()

    private var lastDropletTime: Long = 0

    private val convertedTouchPosition: Vector3 = Vector3()

    init {

        musicBackground.isLooping = true
//        musicBackground.play()

        rectangleBucket.width = RECTANGLE_BUCKET_WIDTH
        rectangleBucket.height = RECTANGLE_BUCKET_HEIGHT
        rectangleBucket.x = SCREEN_WIDTH / 2f - RECTANGLE_BUCKET_WIDTH / 2f
        rectangleBucket.y = 20f

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
    }

    override fun hide() {}

    override fun show() {}

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        game.spriteBatch.projectionMatrix = camera.combined
        game.spriteBatch.begin()
        game.spriteBatch.draw(textureBucket, rectangleBucket.x, rectangleBucket.y)
        rectanglesDroplets.forEach { game.spriteBatch.draw(textureDroplet, it.x, it.y) }
        game.spriteBatch.end()

        camera.update()

        spawnDroplet()
        handleDroplets()
        handleTouchInput()
        handleKeyboardInput()
        restrainBucketMove()
    }

    private fun handleDroplets() {

        val iterator = rectanglesDroplets.iterator()

        while (iterator.hasNext()) {

            val droplet = iterator.next()
            droplet.y -= 200f * Gdx.graphics.deltaTime

            if (droplet.y + RECTANGLE_DROPLET_HEIGHT < 0) {

                iterator.remove()
            }

            if (droplet.overlaps(rectangleBucket)) {

                soundDroplet.play()
                iterator.remove()
            }
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

    private fun restrainBucketMove() {

        if (rectangleBucket.x < 0f) {

            rectangleBucket.x = 0f
        }

        if (rectangleBucket.x > SCREEN_WIDTH - RECTANGLE_BUCKET_WIDTH) {

            rectangleBucket.x = SCREEN_WIDTH - RECTANGLE_BUCKET_WIDTH
        }
    }

    private fun spawnDroplet() {

        if (TimeUtils.nanoTime() - lastDropletTime <= INTERVAL_BETWEEN_DROPLETS) {

            return
        }

        val rectangleDroplet = Rectangle()
        rectangleDroplet.x = MathUtils.random(0f, SCREEN_WIDTH - RECTANGLE_DROPLET_WIDTH)
        rectangleDroplet.y = SCREEN_HEIGHT
        rectangleDroplet.width = RECTANGLE_DROPLET_WIDTH
        rectangleDroplet.height = RECTANGLE_DROPLET_HEIGHT

        rectanglesDroplets.add(rectangleDroplet)

        lastDropletTime = TimeUtils.nanoTime()
    }

    override fun dispose() {

        textureDroplet.dispose()
        textureBucket.dispose()
        soundDroplet.dispose()
        musicBackground.dispose()
    }

    override fun pause() {}

    override fun resume() {}

    override fun resize(width: Int, height: Int) {}
}