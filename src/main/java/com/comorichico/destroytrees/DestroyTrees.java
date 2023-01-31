package com.comorichico.destroytrees;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import java.util.List;
import net.coreprotect.CoreProtectAPI.ParseResult;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author comorichico
 */
public class DestroyTrees extends JavaPlugin implements Listener{
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }
    
    private CoreProtectAPI getCoreProtect() {
            Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

            // Check that CoreProtect is loaded
            if (plugin == null || !(plugin instanceof CoreProtect)) {
                return null;
            }

            // Check that the API is enabled
            CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
            if (CoreProtect.isEnabled() == false) {
                return null;
            }

            // Check that a compatible version of the API is loaded
            if (CoreProtect.APIVersion() < 9) {
                return null;
            }

            return CoreProtect;
    }
    @EventHandler
    public void onBlockDamage(BlockDamageEvent event){
        CoreProtectAPI api = getCoreProtect();
        if (api != null){
            List<String[]> lookup;
            lookup = api.blockLookup(event.getBlock(),2147483647);
            
            for (String[] result : lookup){
                ParseResult parseResult = api.parseResult(result);
                if (parseResult.getActionId()==1){
                    return;
                }
            }
        }
        WoodList woodList = new WoodList();
        for(Material wood: woodList.getWoodList()){
            if(event.getBlock().getType() == wood){
                if(event.getItemInHand().getType() == Material.WOODEN_AXE){
                    new DestroyTask(event,10).runTaskTimer(this, 0, 1);
                }
            }
        }
    }
}
