package org.dave.bonsaitrees.integration.mods;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dave.bonsaitrees.api.IBonsaiIntegration;
import org.dave.bonsaitrees.api.IBonsaiTreeType;
import org.dave.bonsaitrees.api.ITreeTypeRegistry;
import org.dave.bonsaitrees.api.TreeTypeSimple;
import org.dave.bonsaitrees.misc.ConfigurationHandler;
import org.dave.bonsaitrees.utility.Logz;
import org.dave.bonsaitrees.utility.ResourceLoader;
import org.dave.bonsaitrees.utility.SerializationHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;

// Note: we are not using a BonsaiIntegration annotation here, because we are
// loading and using this class ourselves!
public class JSONIntegration implements IBonsaiIntegration {
    @Override
    public void registerTrees(ITreeTypeRegistry registry) {
        ResourceLoader loader = new ResourceLoader(ConfigurationHandler.treeTypesDir, "assets/bonsaitrees/config/types.d/");
        for(Map.Entry<String, InputStream> entry : loader.getResources().entrySet()) {
            String filename = entry.getKey();
            InputStream is = entry.getValue();

            if (!filename.endsWith(".json")) {
                continue;
            }

            Logz.debug(" > Loading tree type from file: '%s'", filename);
            try {
                TreeTypeSimple treeType = SerializationHelper.GSON.fromJson(new JsonReader(new InputStreamReader(is)), TreeTypeSimple.class);
                registry.registerTreeType(this, treeType);
            } catch(JsonParseException e) {
                Logz.info("Could not load tree type from file '%s': %s", filename, e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void generateTree(IBonsaiTreeType type, World world, BlockPos pos, Random rand) {
    }
}