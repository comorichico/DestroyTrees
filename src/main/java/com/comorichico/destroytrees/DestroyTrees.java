package com.comorichico.destroytrees;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import java.util.List;
import net.coreprotect.CoreProtectAPI.ParseResult;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
        Block block = event.getBlock();
        Location location;
        int range = 6;
        int bottom = 5;
        int top = 20;
        Block searchBlock;
        for(Material wood: woodList.getWoodList()){
            if(block.getType() == wood){
                if(event.getItemInHand().getType() == Material.WOODEN_AXE){
                    location = block.getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    block.breakNaturally(event.getItemInHand());
                    for(int yy = y-bottom; yy <= y+top; yy++){
                        for(int xx = x-range; xx <= x+range; xx++){
                            for(int zz = z-range; zz <= z+range; zz++){
                                searchBlock = event.getPlayer().getWorld().getBlockAt(xx,yy,zz);
                                for(Material wood2: woodList.getWoodList()){
                                    if(searchBlock.getType() == wood2){
                                        searchBlock.breakNaturally(event.getItemInHand());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
