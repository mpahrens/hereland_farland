package com.mpa.hf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Input;


import javax.sound.sampled.AudioFormat;

import be.tarsos.dsp.effects.DelayEffect;
import be.tarsos.dsp.effects.FlangerEffect;
import be.tarsos.dsp.filters.LowPassFS;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.synthesis.AmplitudeLFO;
import be.tarsos.dsp.synthesis.NoiseGenerator;
import com.mpa.dsp.AudioGenerator;
import com.mpa.dsp.SineGenerator;

public class HerelandFarland extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ModelBatch modelBatch;
    Environment environment;
    PerspectiveCamera cam;
    Model model;
    ModelInstance instance;
    CameraInputController camController;
    AssetManager assets = new AssetManager();
    AudioGenerator generator;
    SineGenerator s;
	@Override
	public void create () {
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        ModelLoader<?> loader = new ObjLoader();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        try {
            generator = new AudioGenerator(1024, 0);
            generator.addAudioProcessor(new NoiseGenerator(0.2));
            generator.addAudioProcessor(new LowPassFS(1000, 44100));
            generator.addAudioProcessor(new LowPassFS(1000, 44100));
            generator.addAudioProcessor(new LowPassFS(1000, 44100));
            generator.addAudioProcessor(new SineGenerator(0.05, 220));
            generator.addAudioProcessor(new AmplitudeLFO(10, 0.9));
            s = new SineGenerator(0.2,660);
            generator.addAudioProcessor(s);
            generator.addAudioProcessor(new SineGenerator(0.1, 880));
            generator.addAudioProcessor(new DelayEffect(1.5, 0.4, 44100));
            generator.addAudioProcessor(new AmplitudeLFO());
            generator.addAudioProcessor(new NoiseGenerator(0.02));
            generator.addAudioProcessor(new SineGenerator(0.05, 1760));
            generator.addAudioProcessor(new SineGenerator(0.01, 2460));
            generator.addAudioProcessor(new AmplitudeLFO(0.1, 0.7));
            generator.addAudioProcessor(new DelayEffect(0.757, 0.4, 44100));
            generator.addAudioProcessor(new FlangerEffect(0.1, 0.2, 44100, 4));
            generator.addAudioProcessor(new AudioPlayer(new AudioFormat(44100, 16, 1, true, false)));


        }
        catch(Exception e){
            //shrug
        }
        Gdx.app.log("ok","created");
	}

	@Override
	public void render () {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        generator.run();
        //generator.removeAudioProcessor(s);
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            s.adjustFrequency(100.0);
            Gdx.app.log("ok","lol");
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            s.adjustFrequency(-100.0);
        }
        //generator.addAudioProcessor(s);

	}

    @Override
    public void dispose(){
        modelBatch.dispose();
        model.dispose();
    }
}
