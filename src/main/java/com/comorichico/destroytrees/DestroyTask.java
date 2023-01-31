package com.comorichico.destroytrees;

import java.util.Stack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author comorichico
 */
public class DestroyTask extends BukkitRunnable {
    
    private final BlockDamageEvent event;
    private int counter;
    Stack<Block> blockStackFrom = new Stack<>();
    Stack<Block> blockStackTo = new Stack<>();
    
    public DestroyTask(BlockDamageEvent event, int counter) {
        this.event = event;
        this.blockStackFrom.push(event.getBlock());
        this.counter = counter;
    }
    
    @Override
    public void run() {
        if (counter > 0) {
            counter--;
            Block block;
            if(blockStackFrom.empty()){
                return;
            }
            while(!blockStackFrom.empty()){
                block = blockStackFrom.pop();
                Location location = block.getLocation();
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                block.breakNaturally(event.getItemInHand());
                WoodList woodList = new WoodList();
                for(int xx = x-1; xx <= x+1; xx++){
                    for(int yy = y-1; yy <= y+1; yy++){
                        for(int zz = z-1; zz <= z+1; zz++){
                            Block searchBlock = event.getPlayer().getWorld().getBlockAt(xx,yy,zz);
                            for(Material wood: woodList.getWoodList()){
                                if(searchBlock.getType() == wood){
                                    blockStackTo.push(searchBlock);
                                }
                            }
                        }
                    }
                }
            }
            blockStackFrom = blockStackTo;
        } else {
            this.cancel();
        }
    }
}
