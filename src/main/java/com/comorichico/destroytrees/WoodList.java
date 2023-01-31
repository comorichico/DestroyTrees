package com.comorichico.destroytrees;

import java.util.ArrayList;
import org.bukkit.Material;

/**
 *
 * @author comorichico
 */
public class WoodList {
    ArrayList<Material> woodList = new ArrayList<>();
    public void WoodList(){
    }
    
    public ArrayList<Material> getWoodList(){
        woodList = new ArrayList<>();
        woodList.add(Material.OAK_LOG);
        woodList.add(Material.OAK_LEAVES);
        woodList.add(Material.SPRUCE_LOG);
        woodList.add(Material.SPRUCE_LEAVES);
        woodList.add(Material.BIRCH_LOG);
        woodList.add(Material.BIRCH_LEAVES);
        woodList.add(Material.JUNGLE_LOG);
        woodList.add(Material.JUNGLE_LEAVES);
        woodList.add(Material.ACACIA_LOG);
        woodList.add(Material.ACACIA_LEAVES);
        woodList.add(Material.DARK_OAK_LOG);
        woodList.add(Material.DARK_OAK_LEAVES);
        woodList.add(Material.AZALEA_LEAVES);
        woodList.add(Material.FLOWERING_AZALEA_LEAVES);
        woodList.add(Material.MANGROVE_LOG);
        woodList.add(Material.MANGROVE_LEAVES);
        woodList.add(Material.CRIMSON_STEM);
        woodList.add(Material.WARPED_STEM);
        return woodList;
    }
}
