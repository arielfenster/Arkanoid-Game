# Arkanoid-Game
An Arkanoid-clone game that can read different level-settings files and create the levels accordingly.

## Playing the game
In order to play the game, download the 'ArkanoidGame.jar' file to your computer and run it. <br />
The game is played with the left and right arrows on your keyboard.

## Creating your own levels
The game is pre-programmed with default levels. However, you can create your own levels by supplying a level-settings file containing the various details needed, such as paddle speed, number of balls, blocks formation, etc.

### Custom levels
In order to create your own levels, you need to supply a level definitions text file containing the required information.
Multiple levels can be configured in a single file. <br />
For exmaple (see full details at resources/definitions/easy_level_definitions.txt):

```
START_LEVEL
level_name:Direct Hit
ball_velocities:0,500 - format of (angle,speed)
background:color(black)
paddle_speed:650
paddle_width:160
block_definitions:definitions/standard_block_definitions.txt
blocks_start_x:25 } specifies the position of the first block to create,
blocks_start_y:80 } where x=0 is at the top of the screen and y=0 is at the left side of the screen
row_height:25 - specifies the height of the blocks
num_blocks:1
START_BLOCKS
-------r
END_BLOCKS
END_LEVEL
```
* Every level region starts and ends with the 'START_LEVEL' and 'END_LEVEL' key words, respectively.

### Custom blocks
In addition to creating your own levels, you can design your very own gaming blocks that will be displayed.
Blocks settings are divided into sereval components: (see complete file at /resources/definitions/standard_block_definitions.txt) <br /> 


***1. Default values (optional):***
```
'# default values for blocks
default height:25 width:50 stroke:color(black) hit_points:1
```
* Default values are shared between all types of blocks.
* Notice that the line starts with the keyword 'default'. <br /> <br />


***2. Block values:***
```
# block definitions
bdef symbol:b fill:color(blue)
bdef symbol:y fill:color(yellow)
bdef symbol:r fill:color(red)
bdef symbol:g fill:color(green)
bdef symbol:p fill:color(pink)
bdef symbol:w fill:color(white)
bdef symbol:c fill:color(cyan)
bdef symbol:o fill:color(orange)
bdef symbol:H hit_points:1 fill:image(block_images/image1.jpg)
bdef symbol:l fill:image(block_images/image2.jpg)
```
* Every line starts with the keyeword 'bdef' to signify a block definition.
* Each block can be displayed using a color or an image. The image files are located in 'resources/block_images/' directory. Make sure the image size is the same as the block size.

Different blocks can have multiple hit points, where each hit point defines a different view, using the 'fill-x' setting:
```
bdef symbol:G hit_points:2 fill:color(lightGray) fill-2:color(gray)
bdef symbol:l hit_points:2 fill:image(block_images/zebra.jpg) fill-2:image(block_images/leopard.jpg)
```
For example, the 'G'-block is first presented as a gray block and once hit, it will change its color to light gray. <br /> <br />


***3. Spacers values:***
```
# spacers definitions
sdef symbol:- width:50
```
Spacers help separate blocks and push blocks to different positions in the game, without having to know the exact coordinates of the position.
They are used in the blocks-displaying segment in the level settings:
```
START_BLOCKS
-------r
END_BLOCKS
```
* There is only one blocks segment. Make sure the segment starts and ends with 'START_BLOCKS' and 'END_BLOCKS', respectively. <br />

### Playing your levels
* Add your level settings and block settings to /resources/definitions/ directory.
* Change the paths written in the 'level_sets' file to the paths of your custom files (the paths are relative to the 'resources' directory).
* When ready, open the command line and enter the following command:
```
java -jar ArkanoidGame.jar path-to-your-custom-levels-definitions
```


### Final Notes
* If there is a setting that is written in the block definition and in the default values, the block definition will set the value.
* If there is any missing information regarding the blocks or the level settings, the level cannot be created and so an error will occur, resulting in loading the default levels.
* Make sure each attribute starts in a new line. If there are multiple attributes in a single line, separate them with whitespace.

