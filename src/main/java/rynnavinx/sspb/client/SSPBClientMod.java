package rynnavinx.sspb.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rynnavinx.sspb.client.gui.SSPBGameOptions;


@Environment(EnvType.CLIENT)
public class SSPBClientMod implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("SSPB");
	private static SSPBGameOptions CONFIG;


	public static SSPBGameOptions options() {
		return CONFIG;
	}

	@Override
	public void onInitializeClient() {
		CONFIG = SSPBGameOptions.load();
		CONFIG.updateShadowyness(CONFIG.shadowynessPercent);

		LOGGER.info("[SSPB] Broken dirt path lighting is best dirt path lighting lol");
	}
}
