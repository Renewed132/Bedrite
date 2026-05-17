package pl.olafcio.bedrite.screen.play.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.world.level.storage.LevelStorageAccess;
import net.minecraft.world.level.storage.LevelSummary;
import org.lwjgl.opengl.GL11;
import pl.olafcio.bedrite.screen.play.PlayScreen;
import pl.olafcio.bedrite.screen.play.PlayTab;
import pl.olafcio.bedrite.util.ImAButton;
import pl.olafcio.bedrite.util.StringUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public final class Worlds
       extends PlayTab
{
    public void init() {
        buttons.add(new ImAButton(
                0,
                insideX+paddingX, insideY+paddingY,
                insideWidth-paddingX*2, 30,
                "Create New"
        ) {
            @Override
            public void method_891(Minecraft minecraft, int i, int j) {
                startElementRendering(minecraft, j, mouseY -> {
                    super.method_891(minecraft, i, mouseY);
                });
            }

            // What the fuck is going on here??

            @Override
            public boolean isHovered(Minecraft minecraft, int i, int j) {
                return super.isHovered(minecraft, i, j) && j+scrollY >= insideY;
            }

            @Override
            public boolean method_894(Minecraft minecraft, int i, int j) {
                return super.method_894(minecraft, i, j-scrollY) && j >= insideY;
            }

            @Override
            public void mouseReleased(int mouseX, int mouseY) {
                mc.openScreen(new CreateWorldScreen(screen));
            }
        });

        buttons.add(new ButtonWidget(
                -1,
                insideX+paddingX+1, insideY+paddingY+30+7,
                "Worlds"
        ) {
            @Override
            public void method_891(Minecraft minecraft, int i, int j) {
                startElementRendering(minecraft, () -> {
                    minecraft.textRenderer.method_956(message, x, y, 0xFFFFFFFF);
                });
            }
        });

        appendWorlds();
    }

    private void appendWorlds() {
        LevelStorageAccess levelStorageAccess = this.mc.getCurrentSave();
        List<LevelSummary> worlds = levelStorageAccess.getLevelList();
        Collections.sort(worlds);

        int x = insideX + paddingX;
        int y = insideY + paddingY + 30+7 + mc.textRenderer.fontHeight;

        int index = 0;

        for (LevelSummary world : worlds) {
            y += 3;

            final int ind = index++;

            buttons.add(new ButtonWidget(
                    1,
                    x, y,
                    insideWidth - paddingX*2 - 31, 28,
                    "World " + world.getDisplayName()
            ) {
                @Override
                public void method_891(Minecraft minecraft, int mouseX, int mouseY) {
                    startElementRendering(mc, mouseY, smY -> {
                        //                    fill(0, 0, PlayScreen.this.width, PlayScreen.this.height, 0xffff0000);

                        if (mouseX >= x && smY >= y && mouseX <= x+width && smY <= y+height && mouseY >= insideY && mouseY < insideY+insideHeight)
                            renderShape_hovered();
                        else renderShape();

                        //TODO: Do a screenshot of the world on load and render it here.
                        //      Shouldn't be hard.

                        mc.textRenderer.method_964(world.getDisplayName(), x + 3, y + 5, 0xFFFFFFFF);
                        mc.textRenderer.method_964(StringUtil.capitalize(world.method_261().getGameModeName()), x + 4, y + 8 + mc.textRenderer.fontHeight, 0xFFb0b0b0);

                        Calendar calendar = new Calendar.Builder()
                                .setInstant(world.getLastPlayed())
                                .build();

                        String date;

                        mc.textRenderer.method_964(
                                date = String.format("%s/%s/%s",
                                        StringUtil.padStart(String.valueOf(calendar.get(Calendar.MONTH)), 2, "0"),
                                        StringUtil.padStart(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, "0"),
                                        String.valueOf(calendar.get(Calendar.YEAR)).substring(2)
                                ),
                                x + width - 3 - mc.textRenderer.getStringWidth(date),
                                y + 5,
                                0xFFb0b0b0
                        );

                        String size;

                        mc.textRenderer.method_964(
                                size = "?MB" /*String.valueOf(world.).length())*/,
                                x + width - 3 - mc.textRenderer.getStringWidth(size) - 2,
                                y + 8 + mc.textRenderer.fontHeight,
                                0xFFb0b0b0
                        );
                        //                    GL11.glScissor(0, 0, PlayScreen.this.width, PlayScreen.this.height);
                    });
                }

                private void renderShape() {
                    // Background
                    fill(x-1, y-1, x+width+1, y+height+1, 0xFF191919);
                    fill(x+1, y+1, x+width-1, y+height-1, 0xFF3a3a3a);

                    // Top Left
                    fill(x, y, x+width-1, y+1, 0xFF4f4f4f);
                    fill(x, y, x+1, y+height-1, 0xFF4f4f4f);

                    // TL Dots
                    fill(x+width-1, y, x+width, y+1, 0xFF353535);
                    fill(x, y+height-1, x+1, y+height, 0xFF353535);

                    // Bottom Right
                    fill(x+width-1, y+1, x+width, y+height, 0xFF202020);
                    fill(x+1, y+height-1, x+width, y+height, 0xFF202020);
                }

                private void renderShape_hovered() {
                    // Background
                    fill(x-1, y-1, x+width+1, y+height+1, 0xFFFFFFFF);
                    fill(x, y, x+width, y+height, 0xFF007305);

                    // Top Left
                    fill(x, y, x+width-1, y+1, 0xFF509f53);
                    fill(x, y, x+1, y+height-1, 0xFF509f53);

                    // TL Dots
                    fill(x+width-1, y, x+width, y+1, 0xFF017204);
                    fill(x, y+height-1, x+1, y+height, 0xFF017204);

                    // Bottom Right
                    fill(x+width-1, y+1, x+width, y+height, 0xFF005804);
                    fill(x+1, y+height-1, x+width, y+height, 0xFF005804);
                }

                @Override
                public boolean method_894(Minecraft minecraft, int i, int j) {
                    return super.method_894(minecraft, i, j-scrollY);
                }

                @Override
                public void mouseReleased(int mouseX, int mouseY) {
                    if (mouseY >= insideY && mouseY < insideY+insideHeight)
                        joinWorld(world, ind);
                }
            });

            buttons.add(new ButtonWidget(
                    1,
                    x + insideWidth - paddingX*2 - 29, y,
                    28, 28,
                    "Edit World " + world.getDisplayName()
            ) {
                @Override
                public void method_891(Minecraft minecraft, int mouseX, int mouseY) {
                    startElementRendering(mc, mouseY, smY -> {
                        if (mouseX >= x && smY >= y && mouseX <= x+width && smY <= y+height && mouseY >= insideY && mouseY < insideY+insideHeight)
                            renderShape_hovered();
                        else renderShape();

                        GL11.glBindTexture(3553, mc.textureManager.getTextureFromPath("/gui/bedrock/edit.png"));
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

                        float scale = 28 / 49F * .92F;

                        GL11.glPushMatrix();
                        GL11.glScalef(scale, scale, 1);

                        drawTexture((int) (x/scale) + 2, (int) (y/scale) + 4, 0, 0, (int) (width/scale), (int) (height/scale));

                        GL11.glPopMatrix();
                    });
                }

                private void renderShape() {
                    // Background
                    fill(x-1, y-1, x+width+1, y+height+1, 0xFF191919);
                    fill(x+1, y+1, x+width-1, y+height-1, 0xFF3a3a3a);

                    // Top Left
                    fill(x, y, x+width-1, y+1, 0xFF4f4f4f);
                    fill(x, y, x+1, y+height-1, 0xFF4f4f4f);

                    // TL Dots
                    fill(x+width-1, y, x+width, y+1, 0xFF353535);
                    fill(x, y+height-1, x+1, y+height, 0xFF353535);

                    // Bottom Right
                    fill(x+width-1, y+1, x+width, y+height, 0xFF202020);
                    fill(x+1, y+height-1, x+width, y+height, 0xFF202020);
                }

                private void renderShape_hovered() {
                    // Background
                    fill(x-1, y-1, x+width+1, y+height+1, 0xFFFFFFFF);
                    fill(x, y, x+width, y+height, 0xFF007305);

                    // Top Left
                    fill(x, y, x+width-1, y+1, 0xFF509f53);
                    fill(x, y, x+1, y+height-1, 0xFF509f53);

                    // TL Dots
                    fill(x+width-1, y, x+width, y+1, 0xFF017204);
                    fill(x, y+height-1, x+1, y+height, 0xFF017204);

                    // Bottom Right
                    fill(x+width-1, y+1, x+width, y+height, 0xFF005804);
                    fill(x+1, y+height-1, x+width, y+height, 0xFF005804);
                }

                @Override
                public boolean method_894(Minecraft minecraft, int i, int j) {
                    return super.method_894(minecraft, i, j-scrollY);
                }

                @Override
                public void mouseReleased(int mouseX, int mouseY) {
                    if (mouseY >= insideY && mouseY < insideY+insideHeight)
                        mc.openScreen(new EditWorldScreen(screen, world.getFileName()));
                }
            });

            y += 28;
        }
    }

    public void joinWorld(LevelSummary world, int index) {
        this.mc.openScreen(null);

        String filename = world.getFileName();
        if (filename == null)
            filename = "World" + index;

        String displayname = world.getDisplayName();
        if (displayname == null)
            displayname = "World" + index;

        this.mc.method_2935(filename, displayname, null);
    }
}
