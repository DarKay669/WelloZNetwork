package fr.welloznetwork.venus.lib.render;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.lwjgl.opengl.GL20;

public class GLSLSandboxShader
{
    private final int programId, timeUniform, mouseUniform, resolutionUniform;
    
    public GLSLSandboxShader(String fragmentShaderLocation) throws IOException
    {
        int program = GL20.glCreateProgram();

        GL20.glAttachShader(program, this.createShader(GLSLSandboxShader.class.getResourceAsStream("/assets/shaders/passthrough.vsh"), GL20.GL_VERTEX_SHADER));
        GL20.glAttachShader(program, this.createShader(GLSLSandboxShader.class.getResourceAsStream(fragmentShaderLocation), GL20.GL_FRAGMENT_SHADER));

        GL20.glLinkProgram(program);

        int linked = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);

        if (linked == 0) 
        {
            System.err.println(GL20.glGetProgramInfoLog(program, GL20.glGetProgrami(program, GL20.GL_INFO_LOG_LENGTH)));

            throw new IllegalStateException("Shader failed to link");
        }

        this.programId = program;

        GL20.glUseProgram(program);

        this.timeUniform = GL20.glGetUniformLocation(program, "time");
        this.mouseUniform = GL20.glGetUniformLocation(program, "mouse");
        this.resolutionUniform = GL20.glGetUniformLocation(program, "resolution");

        GL20.glUseProgram(0);
    }
    
    public void useShader(int width, int height, float mouseX, float mouseY, float time)
    {
        GL20.glUseProgram(this.programId);

        GL20.glUniform2f(this.resolutionUniform, width, height);
        GL20.glUniform2f(this.mouseUniform, mouseX / width, 1.0f - mouseY / height);
        GL20.glUniform1f(this.timeUniform, time);
    }
    
    private int createShader(InputStream inputStream, int shaderType) throws IOException
    {
        int shader = GL20.glCreateShader(shaderType);

        GL20.glShaderSource(shader, this.readStreamToString(inputStream));

        GL20.glCompileShader(shader);

        int compiled = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);

        if (compiled == 0)
        {
            System.err.println(GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH)));

            throw new IllegalStateException("Failed to compile shader");
        }

        return shader;
    }
    
    private String readStreamToString(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        byte[] buffer = new byte[512];
        
        int read;

        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) 
        {
            out.write(buffer, 0, read);
        }

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }
}