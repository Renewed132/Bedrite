package pl.olafcio.bedrite;

import java.lang.annotation.Repeatable;

/**
 * A Minecraft Bedrock Edition feature.
 */
@Repeatable(Features.class)
public @interface Feature {
    String value();
}
