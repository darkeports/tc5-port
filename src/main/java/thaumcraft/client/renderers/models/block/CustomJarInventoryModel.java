/*     */ package thaumcraft.client.renderers.models.block;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel.Builder;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.Attributes;
/*     */ import net.minecraftforge.client.model.IModel;
/*     */ import net.minecraftforge.client.model.ISmartItemModel;
/*     */ import net.minecraftforge.client.model.ModelLoaderRegistry;
/*     */ import thaumcraft.api.aspects.Aspect;
/*     */ import thaumcraft.api.aspects.AspectList;
/*     */ import thaumcraft.client.lib.models.IInitializeBakedModel;
/*     */ import thaumcraft.common.blocks.BlockTC;
/*     */ import thaumcraft.common.blocks.devices.BlockJarItem;
/*     */ 
/*     */ public class CustomJarInventoryModel implements ISmartItemModel, IInitializeBakedModel
/*     */ {
/*     */   ItemCameraTransforms transforms;
/*     */   List<BakedQuad> faceQuads;
/*     */   List<BakedQuad> generalQuads;
/*     */   TextureAtlasSprite iconSprite;
/*     */   public ResourceLocation MA;
/*     */   
/*     */   public CustomJarInventoryModel(String variant)
/*     */   {
/*  39 */     this.MA = new ResourceLocation("thaumcraft:block/" + variant);
/*     */   }
/*     */   
/*     */ 
/*     */   public void initialize(ItemCameraTransforms cameraTransforms, ResourceLocation icon, ModelManager modelManager)
/*     */   {
/*  45 */     this.transforms = cameraTransforms;
/*  46 */     this.iconSprite = modelManager.getTextureMap().getAtlasSprite(icon.toString());
/*  47 */     this.faceQuads = new ArrayList();
/*  48 */     this.generalQuads = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  53 */   Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function()
/*     */   {
/*     */     public TextureAtlasSprite apply(ResourceLocation location)
/*     */     {
/*  57 */       return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
/*     */     }
/*     */   };
/*     */   
/*  61 */   static HashMap<String, IBakedModel> cache = new HashMap();
/*     */   
/*     */   public IBakedModel handleItemState(ItemStack stack)
/*     */   {
/*  65 */     IBakedModel m = null;
/*  66 */     BlockJarItem item = (BlockJarItem)stack.getItem();
/*  67 */     String key = stack.getMetadata() + "";
/*     */     
/*  69 */     if (item.getAspects(stack) != null) {
/*  70 */       key = key + ":" + item.getAspects(stack).getAspects()[0].getName();
/*  71 */       if (item.getAspects(stack).visSize() > 0) {
/*  72 */         key = key + ":" + item.getAspects(stack).visSize();
/*     */       }
/*     */     }
/*     */     
/*  76 */     if (cache.containsKey(key)) { return (IBakedModel)cache.get(key);
/*     */     }
/*  78 */     SimpleBakedModel.Builder b = new SimpleBakedModel.Builder(this, null);
/*     */     try
/*     */     {
/*  81 */       IModel model = ModelLoaderRegistry.getModel(getModel(stack));
/*     */       
/*  83 */       IBakedModel bm = model.bake(model.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT, this.textureGetter);
/*  84 */       List<BakedQuad> list = bm.getGeneralQuads();
/*  85 */       for (BakedQuad bq : list) {
/*  86 */         b.addGeneralQuad(bq);
/*     */       }
/*     */       
/*  89 */       m = b.makeBakedModel();
/*     */       
/*  91 */       cache.put(key, m);
/*     */     }
/*     */     catch (IOException e) {}
/*  94 */     return m;
/*     */   }
/*     */   
/*     */   public ResourceLocation getModel(ItemStack stack) {
/*  98 */     BlockJarItem item = (BlockJarItem)stack.getItem();
/*  99 */     BlockTC block = (BlockTC)item.block;
/*     */     
/* 101 */     ResourceLocation modelresourcelocation = new ResourceLocation("Thaumcraft:block/" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false));
/*     */     
/*     */ 
/* 104 */     if ((item.getAspects(stack) != null) && (item.getAspects(stack).visSize() > 0))
/*     */     {
/* 106 */       if (item.getAspects(stack).visSize() < 16)
/*     */       {
/* 108 */         modelresourcelocation = new ResourceLocation("Thaumcraft:block/" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_0");
/*     */ 
/*     */       }
/* 111 */       else if (item.getAspects(stack).visSize() < 32)
/*     */       {
/* 113 */         modelresourcelocation = new ResourceLocation("Thaumcraft:block/" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_1");
/*     */ 
/*     */       }
/* 116 */       else if (item.getAspects(stack).visSize() < 48)
/*     */       {
/* 118 */         modelresourcelocation = new ResourceLocation("Thaumcraft:block/" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_2");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 123 */         modelresourcelocation = new ResourceLocation("Thaumcraft:block/" + block.getStateName(block.getStateFromMeta(stack.getMetadata()), false) + "_3");
/*     */       }
/*     */     }
/*     */     
/* 127 */     return modelresourcelocation;
/*     */   }
/*     */   
/*     */   public List getFaceQuads(EnumFacing face)
/*     */   {
/* 132 */     return this.faceQuads;
/*     */   }
/*     */   
/*     */   public List getGeneralQuads()
/*     */   {
/* 137 */     return this.generalQuads;
/*     */   }
/*     */   
/*     */   public boolean isAmbientOcclusion()
/*     */   {
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isGui3d()
/*     */   {
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isBuiltInRenderer()
/*     */   {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture()
/*     */   {
/* 157 */     return this.iconSprite;
/*     */   }
/*     */   
/*     */   public ItemCameraTransforms getItemCameraTransforms()
/*     */   {
/* 162 */     return this.transforms;
/*     */   }
/*     */ }


/* Location:              C:\Users\Alex\Desktop\Thaumcraft-1.8.9-5.2.4-deobf.jar!\thaumcraft\client\renderers\models\block\CustomJarInventoryModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */