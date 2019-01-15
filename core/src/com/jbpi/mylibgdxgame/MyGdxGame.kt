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

const val INTERVAL_BETWEEN_DROPLETS = 1000000000

class MyGdxGame : ApplicationAdapter() {

    private lateinit var textureDroplet: Texture
    private lateinit var textureBucket: Texture
    private lateinit var soundDroplet: Sound
    private lateinit var musicBackground: Music

    private val rectangleBucket = Rectangle()
    private val rectanglesDroplets = com.badlogic.gdx.utils.Array<Rectangle>()

    private val camera = OrthographicCamera()

    private lateinit var spriteBatch: SpriteBatch

    private var lastDropletTime: Long = 0

    private val convertedTouchPosition: Vector3 = Vector3()

    override fun create() {

        textureDroplet = Texture(Gdx.files.internal("droplet.png"))
        textureBucket = Texture(Gdx.files.internal("bucket.png"))
        soundDroplet = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
        musicBackground = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"))

        musicBackground.isLooping = true
//        musicBackground.play()

        rectangleBucket.width = RECTANGLE_BUCKET_WIDTH
        rectangleBucket.height = RECTANGLE_BUCKET_HEIGHT
        rectangleBucket.x = SCREEN_WIDTH / 2f - RECTANGLE_BUCKET_WIDTH / 2f
        rectangleBucket.y = 20f

        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)

        spriteBatch = SpriteBatch()

        spawnDroplet()
    }

    override fun render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        spriteBatch.projectionMatrix = camera.combined
        spriteBatch.begin()
        spriteBatch.draw(textureBucket, rectangleBucket.x, rectangleBucket.y)
        rectanglesDroplets.forEach { spriteBatch.draw(textureDroplet, it.x, it.y) }
        spriteBatch.end()

        camera.update()

        if (TimeUtils.nanoTime() - lastDropletTime > INTERVAL_BETWEEN_DROPLETS) {

            spawnDroplet()
        }

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

    override fun dispose() {

        textureDroplet.dispose()
        textureBucket.dispose()
        soundDroplet.dispose()
        musicBackground.dispose()
        spriteBatch.dispose()
    }

    private fun spawnDroplet() {

        val rectangleDroplet = Rectangle()
        rectangleDroplet.x = MathUtils.random(0f, SCREEN_WIDTH - RECTANGLE_DROPLET_WIDTH)
        rectangleDroplet.y = SCREEN_HEIGHT
        rectangleDroplet.width = RECTANGLE_DROPLET_WIDTH
        rectangleDroplet.height = RECTANGLE_DROPLET_HEIGHT

        rectanglesDroplets.add(rectangleDroplet)

        lastDropletTime = TimeUtils.nanoTime()
    }
}
