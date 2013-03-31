package settii.logic;

import java.util.ArrayList;
import settii.logic.messaging.Message;
import settii.utils.actions.*;

/**
 *
 * @author Merioksan Mikko
 */
public class EnemyManager {
    //private SettiLogic logic;
    
    //private static int SEQUENCE_MODE = 1;
    //private static int RANDOM_MODE = 2;
    //private static int BOSS_MODE = 3;
    //private int mode;
    
    private ActionSequence currentSequence;
    
    private long totalElapsedTime;
    //private long sinceLastSpawn;
    //private long spawnDelay;
    
    private int bossCount;
    //private long currentBossID;
    //public static long NO_BOSS = -1;
    
    // a list of enemies that are currently possible to spawn. Updated as the game progresses
    private ArrayList<String> enemyPool;
    
    private ArrayList<String> bossPool;
    
    public EnemyManager(SettiLogic sl) {
        //logic = sl;
        
        enemyPool = new ArrayList<String>();
        bossPool = new ArrayList<String>();
    }
    
    public void update(long deltaMs) {
        totalElapsedTime += deltaMs;
        //sinceLastSpawn += deltaMs;
        
        updateEnemyPool();
        
        if(currentSequence != null) {
            currentSequence.update(deltaMs);
        }
        
        //if(sinceLastSpawn >= spawnDelay) {
            //spawnEnemies();
        //}
    }
    /*
    public void spawnEnemies() {
        if(mode == SEQUENCE_MODE) {
            //System.out.println("SEQU");
            sequenceMode();
        }
        else if(mode == BOSS_MODE) {
            //System.out.println("BOSS");
            if(!checkBossStatus()) {
                spawnBoss();
            }
            else {
                sinceLastSpawn = 0;
            }
        }
        else {
            //System.out.println("RAND");
            randomMode();
        }
    }
    
    private void sequenceMode() {
        if(currentSequence == null || currentSequence.empty()) {
            mode = RANDOM_MODE;
        }
        else {
            EnemySpawn spawn = currentSequence.getCurrent();
            if(logic.getSector(spawn.sector).free()) {
                logic.getSector(spawn.sector).spawnEnemy(spawn.resource);
                sinceLastSpawn = 0;
                currentSequence.next();
                if(currentSequence.empty()) {
                    mode = RANDOM_MODE;
                }
            }
        }
    }
    
    private void randomMode() {
        int r = Application.get().getRNG().nextInt(4) + 1;
        GameSector sector = logic.getSector((long)r);
        if(sector.free()) {
            r = Application.get().getRNG().nextInt(enemyPool.size());
            sector.spawnEnemy(enemyPool.get(r));
            sinceLastSpawn = 0;
        }
    }
    */
    public String getNextBoss() {
        bossCount++;
        return bossPool.get(bossCount - 1);
    }
    /*
    private void spawnBoss() {
        int r = Application.get().getRNG().nextInt(4) + 1;
        GameSector sector = logic.getSector((long)r);
        if(sector.free()) {
            currentBossID = sector.spawnBoss(getNextBoss());
            sinceLastSpawn = 0;
        }
    }
    
    private boolean checkBossStatus() {
        if(currentBossID == NO_BOSS) {
            return false;
        }
        
        if(Application.get().getLogic().getActor(currentBossID) == null) {
            currentBossID = NO_BOSS; // boss is dead!
            mode = RANDOM_MODE;
        }
        
        return true;
    }
    */
    public void updateEnemyPool() {
        /*
        if(totalElapsedTime > 60000 && enemyPool.size() < 2) {
            //Application.get().getEventManager().queueEvent(new NewMessageEvent(new Message("Disease spreading!", "There seems to be some disease spreading among our enemies in the North sector. Bad news is, it just makes them deadlier!", true)));
            enemyPool.add("assets/data/actors/diseasedCock.xml");
        }
        else if(totalElapsedTime > 120000 && enemyPool.size() < 3) {
            //Application.get().getEventManager().queueEvent(new NewMessageEvent(new Message("Mutated enemies!", "Oh my! Some of the attacking cocks have been mutated quite horribly!", true)));
            enemyPool.add("assets/data/actors/mutantCock.xml");
        }
        else if(totalElapsedTime > 180000 && enemyPool.size() < 4) {
            enemyPool.add("assets/data/actors/crazyCock.xml");
        }
        * 
        */
    }
    public ArrayList<String> getEnemyPool() {
        return enemyPool;
    }
    public void addEnemy(String enemy) {
        enemyPool.add(enemy);
    }
    
    public void start() {
        //spawnDelay = 5000; // default delay between enemy spawns
        totalElapsedTime = 0;
        //sinceLastSpawn = 0;
        
        //currentBossID = NO_BOSS;
        bossCount = 0;
        bossPool.clear();
        for(int i = 1; i <= 5; i++) {
            bossPool.add("assets/data/actors/boss" + i + ".xml");
        }
        
        enemyPool.clear();
        enemyPool.add("assets/data/actors/cock.xml");
        
        //Application.get().getEventManager().queueEvent(new NewMessageEvent(new Message("Attack incoming", "Alright, the first batch of enemies is coming from the north. Time to defend ourselves from these cocks!", true)));
        //mode = SEQUENCE_MODE;
        //currentSequence = new Sequence(Sequence.MANUAL_SEQUENCE, new EnemySpawn(1, "assets/data/actors/enemy.xml"), new EnemySpawn(1, "assets/data/actors/enemy.xml"), new EnemySpawn(1, "assets/data/actors/enemy.xml"), new EnemySpawn(1, "assets/data/actors/enemy.xml"), new EnemySpawn(1, "assets/data/actors/enemy.xml"));
        currentSequence = new ActionSequence(
            new DelayAction(3000),
            new MessageAction(new Message("Attack incoming", "Alright, the first batch of enemies is coming from the north. Time to defend ourselves from these cocks!", true)),
            new DelayAction(3000),
            new MultipleEnemySpawnsAction(1L, "assets/data/actors/cock.xml", 3000, 4),
            new MessageAction(new Message("Enemies inbound!", "They are spreading their forces, enemies coming in from the east!", true)),
            new DelayAction(1000),
            new MultipleEnemySpawnsAction(2L, "assets/data/actors/cock.xml", 3000, 4),
            new DelayAction(1000),
            new MessageAction(new Message("They're everywhere!", "They are spreading even more. Cocks incoming from all directions!", true)),
            new EnemySpawnAction(3L, "assets/data/actors/cock.xml"),
            new DelayAction(2000),
            new EnemySpawnAction(4L, "assets/data/actors/cock.xml"),
            new DelayAction(2000),
            new RandomEnemySpawnAction(20000, 2000, this),
            new MessageAction(new Message("A huge cock!", "Something big is approaching...")),
            new DelayAction(3000),
            new BossSpawnAction(this),
            new MessageAction(new Message("Well done!", "The horrible abomination is dead, but the fight is far from over. Enemies incoming!", true)),
            new MessageAction(new Message("Disease spreading!", "There seems to be some disease spreading among these cocks in the north sector. Bad news is, it just makes them deadlier!", true)),
            new NewEnemyAction("assets/data/actors/diseasedCock.xml", this),
            new DelayAction(1000),
            new MultipleEnemySpawnsAction(1L, "assets/data/actors/diseasedCock.xml", 4000, 5),
            new RandomEnemySpawnAction(40000, 2000, this),
            new MessageAction(new Message("A huge cock!", "Something big is approaching...")),
            new DelayAction(3000),
            new BossSpawnAction(this),
            new MessageAction(new Message("Great job!", "Another monster lies dead, but the fight continues still.", true))
        );
    }
}
