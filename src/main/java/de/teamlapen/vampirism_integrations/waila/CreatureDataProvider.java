package de.teamlapen.vampirism_integrations.waila;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import java.util.List;

/**
 * Provide data for creatures
 */
class CreatureDataProvider implements IEntityComponentProvider {

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(WailaPlugin.getShowCreatureInfoConf())) {
            if (accessor.getEntity() instanceof PathfinderMob && VReference.VAMPIRE_FACTION.getPlayerCapability(accessor.getPlayer()).map(IFactionPlayer::getLevel).orElse(0) > 0) {
                VampirismAPI.getExtendedCreatureVampirism((PathfinderMob) accessor.getEntity()).ifPresent(c -> {
                    int blood = c.getBlood();
                    if (c.hasPoisonousBlood()) {
                        tooltip.addLine(Component.translatable("text.vampirism.blood.poisonous"));
                    } else if (blood > 0) {
                        tooltip.addLine(Component.literal(String.format("%s%s: %d", ChatFormatting.RED, UtilLib.translate("text.vampirism.entitysblood"), blood)));
                    }
                });

            }
        }
    }



}