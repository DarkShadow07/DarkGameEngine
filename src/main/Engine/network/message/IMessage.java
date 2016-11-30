package main.Engine.network.message;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import io.netty.buffer.ByteBuf;

public interface IMessage<MSG extends IMessage, REPLY extends IMessage>
{
	void writeBytes(ByteBuf buf);

	void readBytes(ByteBuf buf);

	@Nullable
	REPLY onMessage(@NotNull MSG msg);
}
