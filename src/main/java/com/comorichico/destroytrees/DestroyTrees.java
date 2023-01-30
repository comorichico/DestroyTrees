package com.comorichico.destroytrees;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
    Stack<Block> blockStack = new Stack<>();
    ArrayList<Block> blockList = new ArrayList<>();
    
    ArrayList<Material> woodList = new ArrayList<>();
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        woodList.add(Material.OAK_LOG);
        //woodList.add(Material.OAK_LEAVES);
        woodList.add(Material.SPRUCE_LOG);
        //woodList.add(Material.SPRUCE_LEAVES);
        woodList.add(Material.BIRCH_LOG);
        //woodList.add(Material.BIRCH_LEAVES);
        woodList.add(Material.JUNGLE_LOG);
        //woodList.add(Material.JUNGLE_LEAVES);
        woodList.add(Material.ACACIA_LOG);
        //woodList.add(Material.ACACIA_LEAVES);
        woodList.add(Material.DARK_OAK_LOG);
        //woodList.add(Material.DARK_OAK_LEAVES);
        //woodList.add(Material.AZALEA_LEAVES);
        //woodList.add(Material.FLOWERING_AZALEA_LEAVES);
        woodList.add(Material.MANGROVE_LOG);
        //woodList.add(Material.MANGROVE_LEAVES);
        woodList.add(Material.CRIMSON_STEM);
        woodList.add(Material.WARPED_STEM);
        
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
        blockList = new ArrayList<>();
        for(Material wood: woodList){
            if(event.getBlock().getType() == wood){
                if(event.getItemInHand().getType() == Material.WOODEN_AXE){
                    blockStack.push(event.getBlock());
                    while(!blockStack.isEmpty()){
                        Block block = blockStack.pop();
                        blockList.add(block);
                        Location location = block.getLocation();
                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();
                        for(int xx = x-1; xx <= x+1; xx++){
                            for(int yy = y-1; yy <= y+1; yy++){
                                for(int zz = z-1; zz <= z+1; zz++){
                                    Block searchBlock = event.getPlayer().getWorld().getBlockAt(xx,yy,zz);
                                    for(Material wood2: woodList){
                                        if(searchBlock.getType() == wood2){
                                            boolean check = true;
                                            for(Block checkedBlock: blockList){
                                                if(checkedBlock.getLocation().getBlockX() == searchBlock.getLocation().getBlockX()
                                                        && checkedBlock.getLocation().getBlockY() == searchBlock.getLocation().getBlockY()
                                                        && checkedBlock.getLocation().getBlockZ() == searchBlock.getLocation().getBlockZ()){
                                                    check = false;
                                                    break;
                                                }
                                            }
                                            if(check){
                                                blockStack.push(searchBlock);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for(Block checkedBlock: blockList){
                        checkedBlock.breakNaturally(event.getItemInHand());
                    }
                }
            }
        }
    }
}
