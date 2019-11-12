import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    //The animal's age
    private int age;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Return the animal's age.
     * @return int - the animal's age.
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * Sets the animal's age.
     * @param newAge - set age to newAge
     */
    protected void setAge(int newAge)
    {
        age = newAge;
    }
    
    /**
     * Add 1 to the animal's age.
     * 
     */
    protected void increaseAge()
    {
        age++;
    }
    
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract protected int getBreedingAge();
    
    /**
     * An animal can breed if it has reached its breeding age.
     * @return boolean - Can this animal breed?
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Increase the age of this animal.  This could result in the animals death.
     */
    protected void incrementAge()
    {
        increaseAge();
        if(age > getMaxAge())
        {
            setDead();
        }
    }
    
    /**
     * Get this animals max age. This could result in its death.
     * @return int - This animal's max age.
     */
    abstract protected int getMaxAge();
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return int - The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability())
        {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Get the animal's breeding probability.
     * @return double - animal's breeding probability.
     */
    abstract protected double getBreedingProbability();
    
    /**
     * Get the animal's max litter size.
     * @return int - max litter size.
     */
    abstract protected int getMaxLitterSize();
}
