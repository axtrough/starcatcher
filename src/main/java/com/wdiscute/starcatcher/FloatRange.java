package com.wdiscute.starcatcher;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;

public record FloatRange(float min, float max) {
    public static final Codec<FloatRange> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.FLOAT.fieldOf("min").forGetter(FloatRange::min),
            Codec.FLOAT.fieldOf("max").forGetter(FloatRange::max)
    ).apply(i, FloatRange::new));
    public static final StreamCodec<ByteBuf, FloatRange> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, FloatRange::min,
            ByteBufCodecs.FLOAT, FloatRange::max,
            FloatRange::new
    );

    public static FloatRange between(float min, float max) {
        return new FloatRange(min, max);
    }

    public static FloatRange exactly(float v) {
        return new FloatRange(v, v);
    }

    public float lerp(float scalar) {
        return Mth.lerp(scalar, min, max);
    }
}
