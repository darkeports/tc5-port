/*    */ package thaumcraft.common.items.tools;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ import thaumcraft.api.aspects.Aspect;
/*    */ import thaumcraft.api.aspects.AspectList;
/*    */ import thaumcraft.api.aspects.IAspectContainer;
/*    */ import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.codechicken.libold.raytracer.RayTracer;
/*    */ import thaumcraft.common.Thaumcraft;
/*    */ import thaumcraft.common.tiles.essentia.TileTubeBuffer;
/*    */ 
/*    */ public class ItemResonator extends Item
/*    */ {
/*    */   public ItemResonator()
/*    */   {
/* 26 */     setMaxStackSize(1);
/* 27 */     setHasSubtypes(false);
/* 28 */     setMaxDamage(0);
/* 29 */     setCreativeTab(Thaumcraft.tabTC);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public EnumRarity getRarity(ItemStack itemstack)
/*    */   {
/* 36 */     return EnumRarity.UNCOMMON;
/*    */   }
/*    */   
/*    */   public boolean hasEffect(ItemStack par1ItemStack)
/*    */   {
/* 41 */     return par1ItemStack.hasTagCompound();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float par8, float par9, float par10)
/*    */   {
/* 48 */     net.minecraft.tileentity.TileEntity tile = world.getTileEntity(pos);
/* 49 */     if ((tile != null) && ((tile instanceof IEssentiaTransport))) {
/* 50 */       if (world.isRemote) {
/* 51 */         player.swingItem();
/* 52 */         return super.onItemUseFirst(itemstack, player, world, pos, side, par8, par9, par10);
/*    */       }
/* 54 */       IEssentiaTransport et = (IEssentiaTransport)tile;
/*    */       
/* 56 */       MovingObjectPosition hit = RayTracer.retraceBlock(world, player, pos);
/* 57 */       if ((hit != null) && (hit.subHit >= 0) && (hit.subHit < 6))
/*    */       {
/* 59 */         side = EnumFacing.VALUES[hit.subHit];
/*    */       }
/*    */       
/* 62 */       if ((!(tile instanceof TileTubeBuffer)) && (et.getEssentiaType(side) != null)) {
/* 63 */         player.addChatMessage(new ChatComponentTranslation("tc.resonator1", new Object[] { "" + et.getEssentiaAmount(side), et.getEssentiaType(side).getName() }));
/*    */       }
/* 65 */       else if (((tile instanceof TileTubeBuffer)) && (((IAspectContainer)tile).getAspects().size() > 0)) {
/* 66 */         for (Aspect aspect : ((IAspectContainer)tile).getAspects().getAspectsSortedByName()) {
/* 67 */           player.addChatMessage(new ChatComponentTranslation("tc.resonator1", new Object[] { "" + ((IAspectContainer)tile).getAspects().getAmount(aspect), aspect.getName() }));
/*    */         }
/*    */       }
/*    */       
/*    */ 
/* 72 */       String s = StatCollector.translateToLocal("tc.resonator3");
/* 73 */       if (et.getSuctionType(side) != null)
/* 74 */         s = et.getSuctionType(side).getName();
/* 75 */       player.addChatMessage(new ChatComponentTranslation("tc.resonator2", new Object[] { "" + et.getSuctionAmount(side), s }));
/*    */       
/*    */ 
/* 78 */       world.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), "thaumcraft:alembicknock", 0.5F, 1.9F + world.rand.nextFloat() * 0.1F);
/*    */       
/*    */ 
/* 81 */       return true;
/*    */     }
/*    */     
/* 84 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\common\items\tools\ItemResonator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */