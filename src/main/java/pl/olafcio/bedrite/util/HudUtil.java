package pl.olafcio.bedrite.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public enum HudUtil {
    ;

    public static void renderPlayer(Minecraft minecraft, int x, int y, int scale, float yaw, float pitch) {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL); // 2903
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 1);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        float f2 = minecraft.playerEntity.field_3313;
        float f3 = minecraft.playerEntity.yaw;
        float f4 = minecraft.playerEntity.pitch;
//        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
//        DiffuseLighting.enableNormally();
//        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-((float)Math.atan(pitch / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        minecraft.playerEntity.field_3313 = (float)Math.atan(yaw / 40.0f) * 20.0f;
        minecraft.playerEntity.yaw = (float)Math.atan(yaw / 40.0f) * 40.0f;
        minecraft.playerEntity.pitch = pitch;
        minecraft.playerEntity.field_3315 = minecraft.playerEntity.yaw;
        GL11.glTranslatef(0.0f, minecraft.playerEntity.heightOffset, 0.0f);
        EntityRenderDispatcher.INSTANCE.yaw = 180.0f;
        EntityRenderDispatcher.INSTANCE.render(minecraft.playerEntity, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        minecraft.playerEntity.field_3313 = f2;
        minecraft.playerEntity.yaw = f3;
        minecraft.playerEntity.pitch = f4;
        GL11.glPopMatrix();
//        DiffuseLighting.disable();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL); // 32826
    }
}
