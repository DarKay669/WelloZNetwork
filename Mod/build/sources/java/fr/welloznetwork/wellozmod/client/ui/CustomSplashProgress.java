package fr.welloznetwork.wellozmod.client.ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.Thread.State;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.SharedDrawable;
import org.lwjgl.util.glu.GLU;

import cpw.mods.fml.client.SplashProgress;
import cpw.mods.fml.common.EnhancedRuntimeException;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.asm.FMLSanityChecker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welloznetwork.venus.lib.ingame.Custom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ResourceLocation;

@Custom(SplashProgress.class)
@SideOnly(Side.CLIENT)
public class CustomSplashProgress
{
    private static Drawable d;
    private static volatile boolean pause = false, done = false;
    private static Thread thread;
    private static volatile Throwable threadError;
    private static final Lock lock = new ReentrantLock(true);
    private static SplashFontRenderer fontRenderer;
    private static final IResourcePack mcPack = Minecraft.getMinecraft().mcDefaultResourcePack;
    private static final IResourcePack fmlPack = createResourcePack(FMLSanityChecker.fmlLocation);
    private static IResourcePack miscPack;
    private static Texture fontTexture, backgroundTexture, foregroundTexture;
    private static Properties config;
    private static boolean enabled;
    private static int backgroundColor, fontColor, currentIndex = 0;
    public static final Semaphore mutex = new Semaphore(1);
    
    private static final List<String> TIPS_LIST = Arrays.asList("Portez toujours un seau d'eau sur vous lorsque vous allez miner.", "Utilisez des torches pour emp�cher des cr�atures hostiles d'appara�tre aux environs.", "Toujours avoir un lit sur soi pour actualiser le point d'apparition.", "Enchantez votre �quipement pour le rendre plus durable et plus efficace.", "Explorez diff�rents biomes pour trouver des ressources de valeur et des structures uniques.", "Garder un oeil sur votre barre de faim et n'oubliez pas de manger r�guli�rement.", "Avant d'explorer le Nether, �quipez-vous de r�sistance au feu pour faire face � l'environnement hostile.", "Utilisez une arme enchant�e de butin pour augmenter vos chances d'obtenir des r�compenses rares de cr�atures comme des perles de l'Ender, des b�tons de Blaze, et plus encore.", "Construisez un abri r�sistant aux Enderman en pla�ant un toit d'au moins trois blocs de haut pour les emp�cher d'entrer", "Cr�ez un sch�ma de 'minage en bande', en creusant un tunnel horizontal et en laissant un espace de deux blocs entre chaque tunnel, cela maximise vos chances de trouver des minerais pr�cieux en minant.", "Utilisez l'�cran de d�bogage F3 pour suivre votre emplacement et retrouver votre chemin vers la maison.", "Utilisez un bouclier pour bloquer les attaques des cr�atures hostiles et d'autres joueurs.", "Gardez un coffre avec de l'�quipement essentiel pr�s de votre point d'apparition.", "L'�levage d'animaux comme les vaches et les moutons peut fournir une source durable de nourriture et de ressources.", "�vitez de creuser directement vers le bas pour ne pas tomber dans des grottes dangereuses ou des piscines de lave.", "Construisez une ferme de monstres simple avec des plates-formes et des canaux pour collecter efficacement des r�compenses et des points d'exp�rience.", "Fabriquez et utilisez une enclume pour combiner et r�parer vos objets enchant�s.", "Utilisez des outils enchant�s avec Fortune pour augmenter le rendement de ressources pr�cieuses comme les diamants et les �meraudes.", "Si vous manquez de nourriture, vous pouvez cultiver des cultures comme le bl�, les carottes et les pommes de terre pour obtenir une source durable de nourriture.", "Utilisez une houe pour pr�parer des zones de terre pour la plantation.", "Vous pouvez changer votre point d'apparition dans le jeu et passer � l'aube en dormant dans un lit.", "Notch a plus d'un million de followers sur X !", "La construction d'un portail vous permettra de voyager dans une autre dimension - le Nether.", "Manger des c�telettes de porc cuites donne plus de sant� que de les manger crues.", "Ne regardez pas directement les bugs.", "Fabriquez des torches pour �clairer les zones la nuit. Les monstres �viteront les zones autour de ces torches.", "Soyez prudent lorsque vous construisez des structures en laine en plein air, car la foudre des orages peut mettre le feu � la laine.", "Tous les Su�dois n'ont pas les cheveux blonds. Certains, comme Jens de Mojang, ont m�me les cheveux roux !", "Les araign�es ne vous attaqueront pas pendant la journ�e, � moins que vous ne les attaquiez.", "Creuser directement vers le bas ou directement le haut n'est pas une bonne id�e.", "Saviez-vous qu'il y a un wiki Minecraft ?", "L'instrument jou� par le bloc musical d�pend du mat�riau en dessous.", "Les Creepers explosent lorsqu'ils s'approchent de vous !", "Dormir dans un lit la nuit fera avancer rapidement le jeu � l'aube, mais tous les joueurs d'un monde multijoueur doivent dormir des lits en m�me temps.", "Creuser la terre ou le sable avec une pelle est plus rapide qu'� la main !", "Qui est Notch ?", "Si vous avez un seau vide, vous pouvez le remplir de lait d'une vache, d'eau ou de lave !", "Minecraft: Java Edition a battu de nombreux records !", "La pierre de taille r�siste aux boules de feu des Ghasts, ce qui rend utile pour prot�ger les portails.", "Atteignez vos destinations plus rapidement avec un wagonnet et des rails !", "Frappez ces boules de feu des Ghasts en retour !", "Si vous ne trouvez pas de charbon pour vos torches, vous pouvez toujours fabriquer du charbon � partir d'arbres dans un four.", "Certaines personnes c�l�bres jouent � Minecraft !", "Utilisez l'outil appropri� pour la t�che !", "Modifiez les param�tres gamma pour rendre le jeu plus lumineux ou plus sombre.", "�tiez-vous � la Minecon ?", "La poudre d'os (fabriqu� � partir d'un os de squelette) peut �tre utilis� comme engrais et peut faire pousser instantan�ment des choses !", "R�coltez des c�telettes de porc sur les cochons, cuisinez-les et mangez-les pour regagner de la sant�.", "Les Creepers sont n�s d'un bug de programmation.", "Placer deux coffres c�te � c�te fera un grand coffre.", "Est-ce un poulet ou un canard ?", "Cuisez un cactus dans un four pour obtenir de la teinture verte.", "L'obsidienne ne peut �tre extraite qu'avec une pioche en diamant.", "Personne chez Mojang n'a jamais vu le visage de Junkboy.", "Les poules pondent un oeuf toutes les 5-10 minutes.", "R�coltez du cuir sur les vaches et utilisez-le pour fabriquer des armures.", "deadmau5 aime Minecraft !", "Les blocs qui peuvent servir de source de lumi�re feront fondre la neige et la glace. Cela inclut les torches, la pierre lumineuse et les Jack-O'-Lanters.", "Les Pigmen ne vous attaqueront pas, sauf si vous les attaquez.", "Il y aura �ventuellement une mise � jour de ce jeu !", "Donnez un os � un loup pour le dompter. Vous pouvez ensuite le faire s'asseoir ou vous suivre.", "Si vous r�glez la difficult� du jeu sur Pacifique, votre sant� se r�g�n�rera automatiquement, et aucune cr�ature ne sortira la nuit !", "Mojang a plus de r�compenses que de membres du personnel !", "Les Creepers sont la source de poudre � canon la plus facilement obtenue.", "La Minecon 2012 a eu lieu � Paris, en France !", "L'obsidienne est cr��e lorsque de l'eau touche une source de lave.", "Les loups apprivois�s montrent leur sant� en fonction de la position de leur queue. Donnez-leur de la viande pour les soigner."," Le nouveau bureau de Mojang est cool !", "Attaquer un loup fera que tous les loups dans les environs se retourneront et vous attaqueront. Cette caract�ristique est �galement partag�e par les Zombie Pigmen.", "La lave peut prendre plusieurs minutes pour dispara�tre COMPLET�MENT lorsque la source est retir�e.", ".party() �tait excellent !", "Un seul seau de lave peut �tre utilis� dans un four pour fondre 100 blocs.", "Les zombies et les squelettes peuvent survivre � la lumi�re du jour s'ils sont dans l'eau.", "La Minecon 2011 a eu lieu � Las Vegas, dans le Nevada, aux Etats-Unis !", "Le forum Minecraft a une section d�di�e � la Java Edition.", "Les cl�tures empilables sont d�sormais dans le jeu !", "Personne ne se souvient o� s'est tenue la Minecon 2014.", "Ne regardez pas un Enderman dans les yeux !", "Si un animal ne peut pas se d�placer � plus de 20 blocs dans n'importe quelle direction, il ne dispara�tra pas.", "Il est toujours pr�f�rable de supposer que les rumeurs sont fausses plut�t que de les consid�rer comme vraies !", "Certains animaux vous suivront si vous avez du bl� dans la main !", "La Minecon 2015 a eu lieu � Londres, au Royaume-Uni !", "La Minecon 2016 s'est tenue � Anaheim, en Californie, aux Etats-Unis !", "Musique par C418 !", "Le charbon et le charbon de bois sont les seuls combustibles accept�s par les wagonnets motoris�s. Ils fournissent environ quatre minutes de transit chacun.", "La meilleure source de nourriture dans Minecraft est le rago�t suspect.", "Vous pouvez monter dans des wagonnet sous l'eau.", "N'importe quelle cr�ature peut �tre nomm�e, sauf l'Ender Dragon et les joueurs.", "Il y a 26 tableaux dans le jeu. Ils sont principalement bas�s sur les oeuvres de Kristoffer Zetterstrand.", "La citrouille sculpt�e est le seul bloc du jeu qui peut �tre enchant�.", "Vous avez plus de chances d'attraper du poisson lorsqu'il pleut.", "La voix du Ghast provient d'un chat.", "La taille de Steve est de 1,87 m�tres.", "Porter des bottes en cuir vous emp�chera de couler ou de geler lorsque vous marchez sur de la neige en poudre sur de hautes montagnes.", "Les villageois peuvent dormir dans le Nether.", "Saviez-vous que les golems de fer, les chats et les poules ne subissent pas de d�g�ts en tombant ?", "Les cubes de magma peuvent nager dans l'eau, mais s'ils sont sous deux blocs d'eau, ils meurent.", "Si vous vous entourez de rails, les zombies seront bloqu�s par ce dernier.", "L'oeuf de dragon est l'objet le plus rare dans Minecraft.", "Chaque fois que vous placez un n�nuphar au m�me endroit, sa direction ne changera jamais.", "La chance qu'un mouton rose apparaisse naturellement est de 0,164% (0,1558% pour les moutons adultes et 0,0082% pour les agneaux).", "Les golems de fer ne peuvent pas se noyer.", "Vous obtiendrez plus de nourriture en r�coltant vos cultures avec l'enchantement Fortune.", "Les balises mettent le m�me temps � �tre d�truites, avec ou sans outil.", "Le lancer d'oeuf a �t� ajout� lorsqu'un joueur a dit qu'il mangerait sa cl� USB si Notch l'ajoutait. (Il l'a fait)", "Les citrouilles sont plus rares que les diamants.", "Les sons que font les Enderman sont des enregistrements de personnes disant 'Salut' et 'Quoi de neuf', mais invers�s et d�form�s.", "Dans les premi�res versions de Minecraft, tous les joueurs nomm�es 'Notch' laissent tomber une pomme quand ils meurent.", "Il faut 85 fl�ches pour casser un bouclier.", "Les cubes de magma peuvent br�ler dans un chaudron", "Utilisez un Creeper charg� pour obtenir un cr�ne de Wither garanti.", "Vous pouvez �teindre le feu avec des potions de jet d'eau dans le Nether.", "Si vous frappez un Enderman dans les jambes sans regarder son visage, il ne se t�l�portera pas lorsque vous continuerez � l'attaquer.", "Lorsque Minecraft a �t� cr�� pour la premi�re fois, il s'appelait 'Cave Game'.", "Vous pouvez entendre un bloc musical jusqu'� 65 blocs de distance.", "Les chevaux squelettes ne se noient pas et ne vous �jectent pas lorsqu'ils entrent dans l'eau, comme un cheval normal.", "Il y a une chance qu'une sorci�re laisse tomber une potion de r�sistance au feu si elle est tu�e par le feu.", "Un cheval peut sauter jusqu'� 5 blocs de haut.", "Vous pouvez enchanter un briquet.", "Les coeurs d'un cheval sont des selles.", "Vous pouvez renvoyer le cr�ne bleu du Wither vers le Wither.", "Vous pouvez �lever des chevaux pour maximiser leurs statistiques : saut, vitesse et sant�.", "Les zombies ne peuvent pas marcher sur des rails.", "Les Creepers sont uniquement attaqu�s par les Withers et les golems de neige.", "Les tableaux ne sont pas affect�s par l'eau.", "Si une champimeuh est frapp�e par la foudre, elle se transformera en vache brune.", "Vous pouvez utiliser des �chelles pour vous rattraper et �viter de subir des d�g�ts en tombant.", "Vous pouvez utiliser des bateaux pour traverser les lacs de lave.", "Si vous fabriquez votre propre golem de fer, il ne vous attaquera pas.", "Lancez une potion de r�sistance au feu sur un golem de neige, et il ne mourra pas dans le Nether.", "Les outils en or sont plus rapides que les outils en Netherite.", "Si vous mangez rapidement des fruits du Chorus en tombant, cela vous t�l�portera sur une surface o� vous �viterez les d�g�ts de chute.", "Les capteurs de luminosit� peuvent �tre utilis�s comme combustible.", "Les golems de neige meurent dans les d�serts.", "Les haches infligent le plus de d�g�ts aux armures.", "Les citrouilles et les Jack-O'-Lanterns ont toujours leur tige orient�e au nord-ouest.", "Vous pouvez utiliser les troncs d'arbres de champignons comme combustible.", "Plus la lune est pleine, plus il y a de chances que les cr�atures apparaissent avec des armures et des enchantements.", "Vous ne pouvez pas faire pousser un arbre de la jungle s'il est entour� de neige.", "Les loups sauvages et apprivois�s poursuivront les squelettes.", "Placez un bloc dans la lave, puis un bloc au-dessus, et cassez le bloc du bas. Ensuite, nagez jusqu'au bloc du haut pour voir sous la lave.", "Si vous renommez une cr�ature avec une �tiquette \"Grumm\" ou \"Dinnerbone\", celui-ci sera � l'envers.", "Un golem de neige ne causera des d�g�ts qu'aux Blazes et au Dragon de l'Ender avec ses boules de neige.", "Si un wagonnet de mine va trop vite, il d�raillera.", "Vous pouvez activer les plaques de pression et les boutons avec une fl�che tir�e d'un arc.", "Vous ne pouvez pas ouvrir les coffres si votre chat est assis dessus.", "Vous pouvez utiliser des colorants pour colorer le collier de votre loup.", "Les armures et les outils en or offrent les meilleurs bonus d'enchantement.", "Si vous marchez accroupi sur de la redstone, elle ne s'allumera pas.", "Il y a 12 326 391 (73,4 % de RVB) couleurs possibles pour les armures en cuir", "La mani�re la plus lente de se d�placer est de placer une toile d'araign�e sur de la glace et de s'accroupir.", "Si vous �tes coinc� dans une biome glac�, utilisez un bateau.", "Sculptez des citrouilles avec des cisailles.", "Utilisez un lit pour r�duire les d�g�ts en cas de chute.", "Peu importe comment vous placez les g�teaux, ils auront toujours la m�me orientation.", "Lorsque vous appuyez sur la touche gauche Control sur un objet, vous pouvez avoir des informations � propos de ce dernier.", "Personne ne se soucie de l'endroit o� vous �tes dans la gratte, jusqu'� ce que vous trouviez des diamants.", "Lorsque vous atteignez la Bedrock, la seule voie � suivre est de monter.", "Comment pouvez-vous esp�rer trouver des diamants si vous minez � la mauvaise couche ?", "Plus la raret� est �lev�e, plus il peut infliger de d�g�ts.", "L'armure en Netherite prend plus de temps � fabriquer, mais en vaut la peine dans l'End.", "Tout ce qui est d�truit peut �tre reconstruit.", "Personne ne se soucie de l'endroit o� vous �tes dans la grotte, jusqu'� ce que vous trouviez des diamants.", "Ne cherchez pas de diamants dans une grotte o� des torches ont �t� plac�es.", "Pour arriver au bout, il faut traverser l'enfer.", "Vous pouvez poss�der le monde et �tre quand m�me seul.", "Au premier abord cela peut para�tre inutile mais sans cela rien n'est possible.", "Vous ne pouvez pas choisir o� vous commencez.", "Mais vous pouvez choisir o� vous allez.", "Vous ne pouvez pas dormir pour l'instant, il y a des monstres � proximit�.", "Ne laissez jamais les arbres flotter.", "Vous pouvez r�parer votre armure avant qu'elle ne se brise.", "Vous pouvez perdre vos objets rares. Mais vous ne perdrez jamais les comp�tences n�cessaires pour les r�cup�rer.", "Votre or ne sert � rien tant que vous ne l'�changez pas.", "Technoblade ne meurt jamais.", "Peu importe que vous ayez un manoir en diamant ou une maison en terre. Une fois que vous avez plac� le four � c�t� de l'�tabli, cela devient votre maison.", "Lorsque vous �tes pr�t pour la fin, vous �tes pr�t � tout.", "Les roses sont rouges, les Creepers sont verts, courez avant qu'ils ne vous voient.", "Chaque bloc que vous placez est un pas de plus vers votre cr�ation ultime.", "La seule limite est votre imagination.", "Vous pouvez construire n'importe quoi, il suffit d'y croire.", "Une pioche en fer est plus lente qu'une pioche en or, mais elle durera plus longtemps.", "Manger. Dormir. Minecraft. R�p�ter.", "Pourquoi l'Ender Dragon ne peut-il pas lire un livre ? Parce qu'il commence toujours par la fin.", "Un Creeper entre dans un bar. Tout le monde explose.", "Ce que vous avez mis des ann�es � construire peut �tre d�truit du jour au lendemain. Construisez quand m�me.", "Non ! Non ! Pas un Creeper !", "Merci Mario, mais la princesse est dans un autre ch�teau.", "Les �toiles du Nether ne peuvent pas �tre d�truites par des explosions.", "L'or est tr�s commun dans le Nether.", "La diorite, soit on l'aime, soit on la d�teste.", "Ne vous inqui�tez pas des plantages sur la b�ta, cela signifie moins de plantages plus tard.", "PNJ signifie Personnage Non Joueur.", "La pluie de ton sarcasme glisse sur la toile cir�e de mon indiff�rence.", "La redstone peut �tre utilis�e pour cr�er des circuits et des machines simples.", "Le jeu tourne mal ? Essayez de r�duire la distance de rendu dans les param�tres.", "Emportez toujours un lit avec vous lorsque vous explorez le Nether. On ne sait jamais quand on peut avoir besoin d'une sieste", "Appuyez sur F5 pour changer de perspective.", "La plupart des monstres ne sortent que la nuit. Pendant la journ�e, vous �tes en s�curit�... probablement.", "Cassez des blocs, r�cup�rez des ressources, Utilisez un �tabli pour fabriquer des objets utiles.", "Vous avez des vies illimit�es, mais vos objets tombent au sol lorsque vous mourez. D�p�chez-vous de les r�cup�rer !", "Cela semble une bonne id�e de construire un endroit o� vous pourrez passer la nuit en toute s�curit�.", "Utilisez l'outil adapt� � votre travail ! Couper du bois avec une hache est beaucoup plus rapide qu'� la main.", "M�me dans Minecraft, vous aurez faim, alors essayez de trouver quelque chose � manger avant que cela n'arrive.", "Les monstres apparaissent dans l'obscurit�. Fabriquez une torche pour �gayer n'importe quel espace.", "Les lits sont g�niaux ! Fabriquez-en un pour passer rapidement la nuit et d�finir votre point de r�apparition.", "Soyez prudent lorsque vous nagez. Restez trop longtemps sous l'eau et vous manquerez d'oxyg�ne.", "Le monde de Minecraft est infiniment surprenant. Vous y trouverez de nombreux animaux, plantes et m�me des �f�k�a�b�r �f�k�a�b�r", "Les golems de fer se battront pour vous !", "Donnez un os � un loup pour l'apprivoiser. Vous pourrez ensuite le faire s'asseoir ou vous suivre.", "Porter une citrouille sur la t�te emp�chera les Enderman de vous attaquer.", "R�coltez le cuir des vaches et utilisez-le pour fabriquer des armures et des livres.", "Si le bloc source d'eau ou de lave est retir�, cela arr�tera compl�tement le flux.", "Les blocs pouvant servir de source lumineuse feront fondre la neige et la glace.", "Les loups et les golems de fer n'attaqueront pas les Creepers.", "Des colorants de diff�rentes couleurs peuvent �tre fabriqu�s � partir d'une vari�t� de mat�riaux, faites cuire un cactus dans un four pour obtenir un colorant vert !", "Les Enderman d�testent l'heure du bain !", "Parfois, les golems de fer offrent des fleurs aux enfants des villageois !", "Les ocelots montrent aux Creepers qui est le patron !", "Pr�parez des potions de respiration aquatique pour l'exploration sous-marine !", "Utilisez des cisailles pour sculpter une citrouille.", "Les boussoles pointent toujours vers le point d'apparition du monde, fabriquez-en une pour trouver votre chemin !", "Faites du commerce avec les villageois pour obtenir de la nourriture, des outils et bien plus encore !", "Les laisses peuvent �tre utilis�es sur les bateaux.", "Accroupissez-vous pour marcher en toute s�curit� sur des blocs de magma.", "Vous pouvez ramasser les toiles d'araign�es � l'aide de cisailles.", "Vous pouvez faire fondre 20 objets dans un four en utilisant un bloc d'algue s�ch�e comme combustible.", "Apprivoiser un cheval squelette ne n�cessite pas de selle.", "Courez dans l'eau pour nager !", "Soyez gentil avec les animaux !", "Lorsque vous creusez � la verticale, placez une torche � vos pieds pour briser le sable ou le gravier qui pourrait potentiellement tomber sur vous.", "Les minerais d'�meraude sont rares dans Minecraft ! On ne les trouve que dans les montagnes ou les prairies.", "Besoin de diamants ? Ils deviennent plus courants � mesure que vous creusez plus profond�ment.", "Avant de miner des diamants, de la redstone ou de l'or, assurez-vous d'utiliser une pioche en fer ou en diamant, sinon cela ne fonctionnera pas.", "Vous ne trouvez pas de charbon pour vos torches ? Essayez de mettre quelques b�ches dans un four.", "Des merveilles infinies, des possibilit�s infinies.", "Plantez quelques pousses d'arbres et ils deviendront des arbres.", "La premi�re version de Minecraft a �t� cr��e en seulement 6 jours !", "La langue de l'Enderman est en fait de l'anglais � l'envers.", "Un Spider Jockey est l'apparition rare d'une araign�e mont�e par un squelette !", "Voyager d'un bloc dans le Nether �quivaut � voyager de huit blocs � la surface.", "Les �lytres peuvent �tre utilis�es pour planer, trouvez-les dans les Cit�s de l'End !", "Les monstres tu�s par les fl�ches des squelettes ont une chance de laisser tomber des disques musicaux.", "Les packs de textures modifient l'apparence des blocs, des objets et m�me des menus dans le jeu. Essayez-les !", "L'utilisation des feu d'artifice augmente la vitesse des �lytres dans les airs. Assurez-vous simplement qu'elles n'explosent pas, sinon �a fera TR�S MAL !", "Vous �tes r�ellement entrain de lire ceci ?", "La balise est un objet puissant qui ne peut �tre fabriqu� qu'avec une �toile du Nether en tuant le Wither !", "Mais aussi par Lena Raine !");
    private static String tips = TIPS_LIST.get(0);
    private static long time = 0L;
    private final static Random RANDOM = new Random();
    
    private static String getString(String name, String def)
    {
        String value = config.getProperty(name, def);
        config.setProperty(name, value);
        
        return value;
    }
    
    public static void start()
    {
        File configFile = new File(Minecraft.getMinecraft().mcDataDir, "config/splash.properties");
        FileReader fileReader = null;
        
        config = new Properties();
        
        try
        {
            fileReader = new FileReader(configFile);
            config.load(fileReader);
        }
        
        catch (IOException e)
        {
            FMLLog.info("Could not load splash.properties, will create a default one");
        }
        
        finally
        {
            IOUtils.closeQuietly(fileReader);
        }
        
        enabled = true;
        backgroundColor = Color.WHITE.getRGB();
        fontColor = Color.WHITE.getRGB();
        
        final ResourceLocation fontLoc = new ResourceLocation(getString("fontTexture", "textures/font/ascii.png"));
        final ResourceLocation backgroundLoc = new ResourceLocation("wellozmod:textures/gui/background.jpg");
        final ResourceLocation foregroundLoc = new ResourceLocation("wellozmod:textures/gui/foreground.jpg");
        
        File miscPackFile = new File(Minecraft.getMinecraft().mcDataDir, getString("resourcePackPath", "resources"));
        FileWriter fileWriter = null;
        
        try
        {
            fileWriter = new FileWriter(configFile);
            config.store(fileWriter, "Splash screen properties");
        }
        
        catch (IOException e)
        {
            FMLLog.log(Level.ERROR, e, "Could not save the splash.properties file");
        }
        
        finally
        {
            IOUtils.closeQuietly(fileWriter);
        }
        
        miscPack = createResourcePack(miscPackFile);
        
        if (!enabled)
        {
            return;
        }
        
        FMLCommonHandler.instance().registerCrashCallable(new ICrashCallable()
        {
            public String call() throws Exception
            {
                return "' Vendor: '" + GL11.glGetString(7936) + "' Version: '" + GL11.glGetString(7938) + "' Renderer: '" + GL11.glGetString(7937) + "'";
            }
            
            public String getLabel()
            {
                return "GL info";
            }
        });
        
        CrashReport crashReport = CrashReport.makeCrashReport(new Throwable()
        {
            public String getMessage()
            {
                return "This is just a prompt for computer specs to be printed. THIS IS NOT A ERROR";
            }
            
            public void printStackTrace(PrintWriter printWriter)
            {
                printWriter.println(getMessage());
            }
            
            public void printStackTrace(PrintStream printStream)
            {
                printStream.println(getMessage());
            }
            
        }, "Loading screen debug info");
        
        try
        {
            d = new SharedDrawable(Display.getDrawable());
            Display.getDrawable().releaseContext();
            d.makeCurrent();
        }
        
        catch (LWJGLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        Thread mainThread = Thread.currentThread();
        thread = new Thread(new Runnable()
        {
            private float progress = 0.0F, targetProgress = 0.0F;
            private int barWidth = 0, barHeight = 0;
            
            public void run()
            {
                this.setGL();
                fontTexture = new Texture(fontLoc);
                backgroundTexture = new Texture(backgroundLoc);
                foregroundTexture = new Texture(foregroundLoc);
                
                GL11.glEnable(3553);
                fontRenderer = new SplashFontRenderer();
                GL11.glDisable(3553);
                
                while (!done)
                {
                    ProgressManager.ProgressBar first = null, penult = null, last = null;
                    Iterator<ProgressManager.ProgressBar> i = ProgressManager.barIterator();
                    
                    while (i.hasNext())
                    {
                        if (first == null)
                        {
                            first = i.next();
                            continue;
                        }
                        
                        penult = last;
                        last = i.next();
                    }
                    
                    GL11.glClear(16384);
                    
                    int w = Display.getWidth();
                    int h = Display.getHeight();
                    this.barWidth = w - 40;
                    this.barHeight = (int) (h * 0.017F);
                    
                    GL11.glViewport(0, 0, w, h);
                    GL11.glMatrixMode(5889);
                    GL11.glLoadIdentity();
                    GL11.glOrtho((320 - w / 2), (320 + w / 2), (240 + h / 2), (240 - h / 2), -1.0D, 1.0D);
                    GL11.glMatrixMode(5888);
                    GL11.glLoadIdentity();
                    this.setColor(backgroundColor);
                    
                    GL11.glPushMatrix();
                    GL11.glEnable(3553);
                    backgroundTexture.bind();
                    GL11.glBegin(7);
                    backgroundTexture.texCoord(0, 0.0F, 0.0F);
                    GL11.glVertex2f((320 - w / 2), (240 - h / 2));
                    backgroundTexture.texCoord(0, 0.0F, 1.0F);
                    GL11.glVertex2f((320 - w / 2), (240 + h / 2));
                    backgroundTexture.texCoord(0, 1.0F, 1.0F);
                    GL11.glVertex2f((320 + w / 2), (240 + h / 2));
                    backgroundTexture.texCoord(0, 1.0F, 0.0F);
                    GL11.glVertex2f((320 + w / 2), (240 - h / 2));
                    GL11.glEnd();
                    GL11.glDisable(3553);
                    GL11.glPopMatrix();
                    this.setColor(backgroundColor);
                    GL11.glEnable(3553);
                    foregroundTexture.bind();
                    GL11.glBegin(7);
                    foregroundTexture.texCoord(0, 0.0F, 0.0F);
                    GL11.glVertex2f((320 - w / 2), (240 - h / 2));
                    foregroundTexture.texCoord(0, 0.0F, 1.0F);
                    GL11.glVertex2f((320 - w / 2), (240 + h / 2));
                    foregroundTexture.texCoord(0, 1.0F, 1.0F);
                    GL11.glVertex2f((320 + w / 2), (240 + h / 2));
                    foregroundTexture.texCoord(0, 1.0F, 0.0F);
                    GL11.glVertex2f((320 + w / 2), (240 - h / 2));
                    GL11.glEnd();
                    GL11.glDisable(3553);
                    
                    if (first != null)
                    {
                        this.targetProgress = (first.getStep() + 1) / (float) (first.getSteps() + 1);
                        this.progress = this.smoothValue(this.progress, this.targetProgress);
                        
                        GL11.glPushMatrix();
                                                
                        GL11.glTranslated((320 - w / 2 + 20 + this.barWidth) - fontRenderer.getStringWidth((first.getStep() >= first.getSteps()) ? "Chargement" : ((int)Math.ceil((this.progress * 100.0F)) + "%")) * (w * 0.002F), ((240 + h / 2) - h * 0.02F) - fontRenderer.FONT_HEIGHT * (w * 0.002F), 0.0D);
                        this.setColor(fontColor);
                        
                        GL11.glScaled((w * 0.002F), (w * 0.002F), 1.0D);
                        GL11.glEnable(3553);
                        
                        fontRenderer.drawStringWithShadow((first.getStep() >= first.getSteps()) ? "Chargement" : ((int)Math.ceil((this.progress * 100.0F)) + "%"), 0, 0, fontColor);
                        
                        GL11.glPopMatrix();
                    }
                    
                    if (tips != null)
                    {
                        long currentTime = System.currentTimeMillis();
                        
                        if (currentTime - time > 5000L)
                        {
                            int newIndex;
                            
                            do
                            {
                                newIndex = RANDOM.nextInt(TIPS_LIST.size());
                            }
                            
                            while (newIndex == currentIndex && TIPS_LIST.size() > 1);
                            
                            currentIndex = newIndex;
                            tips = TIPS_LIST.get(currentIndex);
                            time = currentTime;
                        }
                        
                        GL11.glPushMatrix();
                        GL11.glTranslatef((320 - w / 2) + w * 0.037F, 240.0F - h * 0.022F, 0.0F);
                        GL11.glScaled(2.0D, 2.0D, 2.0D);
                        
                        List<String> texts = this.getSplittedString(tips, 220.0D);
                        
                        for (int lineIndex = 0; lineIndex < texts.size(); lineIndex++)
                        {
                            fontRenderer.drawStringWithShadow(texts.get(lineIndex), 0, (int) (lineIndex * 10.0F), -1);
                        }
                        
                        GL11.glPopMatrix();
                    }
                    
                    mutex.acquireUninterruptibly();
                    Display.update();
                    mutex.release();
                    
                    if (pause)
                    {
                        this.clearGL();
                        this.setGL();
                    }
                    
                    Display.sync(100);
                }
                
                this.clearGL();
            }
            
            private List<String> getSplittedString(String text, double width)
            {
                text = this.trimStringNewLine(text);
                
                List<String> list = fontRenderer.listFormattedStringToWidth(text, (int) width);
                
                return list;
            }
            
            private String trimStringNewLine(String text)
            {
                while (text != null && text.endsWith("\n"))
                {
                    text = text.substring(0, text.length() - 1);
                }
                
                return text;
            }
            
            private void setColor(int color)
            {
                GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));
            }
            
            private void drawBox(float w, float h)
            {
                GL11.glBegin(7);
                GL11.glVertex2f(0.0F, 0.0F);
                GL11.glVertex2f(0.0F, h);
                GL11.glVertex2f(w, h);
                GL11.glVertex2f(w, 0.0F);
                GL11.glEnd();
            }
            
            private void drawBar(ProgressManager.ProgressBar progressBar)
            {
                this.targetProgress = (progressBar.getStep() + 1) / (progressBar.getSteps() + 1);
                this.progress = this.smoothValue(this.progress, this.targetProgress);
                
                GL11.glPushMatrix();
                this.setColor(Color.WHITE.getRGB());
                
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.27F);
                this.drawBox(this.barWidth, this.barHeight);
                
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.setColor((new Color(239, 57, 38)).getRGB());
                this.drawBox(this.barWidth * this.progress, this.barHeight);
                
                GL11.glPopMatrix();
            }
            
            private float smoothValue(float value, float target)
            {
                return value + (target - value) * 0.1F;
            }
            
            private void setGL()
            {
                lock.lock();
                
                try
                {
                    Display.getDrawable().makeCurrent();
                }
                
                catch (LWJGLException e)
                {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                
                GL11.glClearColor((backgroundColor >> 16 & 0xFF) / 255.0F, (backgroundColor >> 8 & 0xFF) / 255.0F, (backgroundColor & 0xFF) / 255.0F, 1.0F);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
            }
            
            private void clearGL()
            {
                Minecraft.getMinecraft().displayWidth = Display.getWidth();
                Minecraft.getMinecraft().displayHeight = Display.getHeight();
                Minecraft.getMinecraft().resize(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
                
                GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(2929);
                GL11.glDepthFunc(515);
                GL11.glEnable(3008);
                GL11.glAlphaFunc(516, 0.1F);
                
                try
                {
                    Display.getDrawable().releaseContext();
                }
                
                catch (LWJGLException e)
                {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                
                finally
                {
                    lock.unlock();
                }
            }
        });
        
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            public void uncaughtException(Thread thread, Throwable throwable)
            {
                FMLLog.log(Level.ERROR, throwable, "Splash thread Exception", new Object[0]);
                threadError = throwable;
            }
        });
        
        thread.start();
        checkThreadState();
    }
    
    private static void checkThreadState()
    {
        if (thread.getState() == State.TERMINATED || threadError != null)
        {
            throw new IllegalStateException("Splash thread", threadError);
        }
    }
    
    public static void finish()
    {
        if (!enabled)
        {
            return;
        }
        
        try
        {
            checkThreadState();
            done = true;
            thread.join();
            d.releaseContext();
            Display.getDrawable().makeCurrent();
            fontTexture.delete();
            backgroundTexture.delete();
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
            
            if (disableSplash())
            {
                throw new EnhancedRuntimeException(e)
                {
                    protected void printStackTrace(WrappedPrintStream wrappedPrintStream)
                    {
                        wrappedPrintStream.println("SplashProgress has detected a error loading Minecraft.");
                        wrappedPrintStream.println("This can sometimes be caused by bad videos drivers.");
                        wrappedPrintStream.println("We have automatically disabled the new Splash Screen in config/splash.properties.");
                        wrappedPrintStream.println("Try reloading Minecraft before reporting any errors.");
                    }
                };
            }
            
            else
            {
                throw new EnhancedRuntimeException(e)
                {
                    protected void printStackTrace(WrappedPrintStream wrappedPrintStream)
                    {
                        wrappedPrintStream.println("SplashProgress has detected a error loading Minecraft.");
                        wrappedPrintStream.println("This can sometimes be caused by bad videos drivers.");
                        wrappedPrintStream.println("Please try disabling the new Splash Screen in config/splash.properties.");
                        wrappedPrintStream.println("After doing so, try reloading Minecraft before reporting any errors.");
                    }
                };
            }
        }
    }
    
    private static boolean disableSplash()
    {
        File configFile = new File(Minecraft.getMinecraft().mcDataDir, "config/splash.properties");
        File parent = configFile.getParentFile();
        
        if (!parent.exists())
        {
            parent.mkdirs();
        }
        
        FileReader fileReader = null;
        enabled = false;
        config.getProperty("enabled", "false");
        
        FileWriter fileWriter = null;
        
        try
        {
            fileWriter = new FileWriter(configFile);
            config.store(fileWriter, "Splash screen properties");
        }
        
        catch (IOException e)
        {
            FMLLog.log(Level.ERROR, e, "Could not save the splash.properties file");
            return false;
        }
        
        finally
        {
            IOUtils.closeQuietly(fileWriter);
        }
        
        return true;
    }
    
    private static IResourcePack createResourcePack(File file)
    {
        if (file.isDirectory())
        {
            return new FolderResourcePack(file);
        }
        
        else
        {
            return new FileResourcePack(file);
        }
    }
    
    private static final IntBuffer intBuffer = BufferUtils.createIntBuffer(4 * 1024 * 1024);
    
    private static class Texture
    {
        private final ResourceLocation resourceLocation;
        private final int name, width, height, frames, size;
        
        public Texture(ResourceLocation resourceLocation)
        {
            InputStream inputStream = null;
            
            try
            {
                this.resourceLocation = resourceLocation;
                inputStream = open(resourceLocation);
                ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
                Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
                
                if (!imageReaders.hasNext())
                {
                    throw new IOException("No suitable reader found for image" + resourceLocation);
                }
                
                ImageReader imageReader = imageReaders.next();
                imageReader.setInput(imageInputStream);
                
                this.frames = imageReader.getNumImages(true);
                
                BufferedImage[] bufferedImages = new BufferedImage[this.frames];
                
                for (int i = 0; i < this.frames; i++)
                {
                    bufferedImages[i] = imageReader.read(i);
                }
                
                imageReader.dispose();
                int size = 1;
                this.width = bufferedImages[0].getWidth();
                this.height = bufferedImages[0].getHeight();
                
                while ((size / this.width) * (size / this.height) < this.frames) size *= 2;
                
                this.size = size;
                
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                
                synchronized(CustomSplashProgress.class)
                {
                    this.name = GL11.glGenTextures();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.name);
                }
                
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, size, size, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (IntBuffer) null);
                checkGLError("Texture creation");
                
                for (int i = 0; i * (size / this.width) < this.frames; i++)
                {
                    for (int j = 0; i * (size / this.width) + j < this.frames && j < size / this.width; j++)
                    {
                        intBuffer.clear();
                        BufferedImage bufferedImage = bufferedImages[i * (size / this.width) + j];
                        
                        for (int k = 0; k < this.height; k++)
                        {
                            for (int l = 0; l < this.width; l++)
                            {
                                intBuffer.put(bufferedImage.getRGB(l, k));
                            }
                        }
                        
                        intBuffer.position(0).limit(this.width * this.height);
                        GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, j * this.width, i * this.height, this.width, this.height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, intBuffer);
                        checkGLError("Texture uploading");
                    }
                }
                
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
            }
            
            catch (IOException e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            
            finally
            {
                IOUtils.closeQuietly(inputStream);
            }
        }
        
        public ResourceLocation getLocation()
        {
            return this.resourceLocation;
        }
        
        public int getName()
        {
            return this.name;
        }
        
        public int getWidth()
        {
            return this.width;
        }
        
        public int getHeight()
        {
            return this.height;
        }
        
        public int getFrames()
        {
            return this.frames;
        }
        
        public int getSize()
        {
            return this.size;
        }
        
        public void bind()
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.name);
        }
        
        public void delete()
        {
            GL11.glDeleteTextures(this.name);
        }
        
        public float getU(int frame, float u)
        {
            return this.width * (frame % (this.size / this.width) + u) / this.size;
        }
        
        public float getV(int frame, float v)
        {
            return this.height * (frame / (this.size / this.width) + v) / this.size;
        }
        
        public void texCoord(int frame, float u, float v)
        {
            GL11.glTexCoord2f(this.getU(frame, u), this.getV(frame, v));
        }
    }
    
    private static class SplashFontRenderer extends FontRenderer
    {
        public SplashFontRenderer()
        {
            super(Minecraft.getMinecraft().gameSettings, fontTexture.getLocation(), null, false);
            super.onResourceManagerReload(null);
        }
        
        protected void bindTexture(ResourceLocation resourceLocation)
        {
            if (resourceLocation != locationFontTexture)
            {
                throw new IllegalArgumentException();
            }
            
            fontTexture.bind();
        }
        
        protected InputStream getResourceInputStream(ResourceLocation resourceLocation) throws IOException
        {
            return Minecraft.getMinecraft().mcDefaultResourcePack.getInputStream(resourceLocation);
        }
    }
    
    public static void drawVanillaScreen() throws LWJGLException
    {
        if (!enabled)
        {
            Minecraft.getMinecraft().loadScreen();
        }
    }
    
    public static void clearVanillaResources(TextureManager textureManager, ResourceLocation resourceLocation)
    {
        if (!enabled)
        {
            textureManager.deleteTexture(resourceLocation);
        }
    }
    
    public static void checkGLError(String where)
    {
        if (GL11.glGetError() != 0)
        {
            throw new IllegalStateException(where + ": " + GLU.gluErrorString(GL11.glGetError()));
        }
    }
    
    private static InputStream open(ResourceLocation resourceLocation) throws IOException
    {
        if (miscPack.resourceExists(resourceLocation))
        {
            return miscPack.getInputStream(resourceLocation);
        }
        
        else if (fmlPack.resourceExists(resourceLocation))
        {
            return fmlPack.getInputStream(resourceLocation);
        }
        
        return mcPack.getInputStream(resourceLocation);
    }
}