# mini CAD
**姓名**: 范钊瑀

**学号**: 3190105838


## 思路与设计
### 布局设计
程序中的整体框架为由三部分组成，最底层的布局为`BorderLayout`，处于EAST位置的是整个的工具栏，工具栏是一个`JPanel`，利用一个5*1的`GridLayout`分为上下两部分，上部分是四个不同功能的按键，用于选择不同的绘图类型，下部分是调色板`palette`，其中共有9个颜色，由另一个3*3的`GridLayout`负责布局，每个`Grid`中都有一个`JButton`，每个`JButton`被涂上了不同的颜色，点击对应的按钮会改变已选择的颜色。

此外，整个窗口的顶部为一个`JMenuBar`，其中拥有对文件操作的菜单按钮，其中的子菜单包含的读取和保存功能按钮和程序的退出按钮，当点击读取和保存的按钮的时候会弹出对应的对话框，负责文件的选择和存储操作。
![](https://raw.githubusercontent.com/FanBB2333/picBed/main/img/20211112175746.png)

### 数据结构

#### `Pair`类
 `Pair`类是一个泛型类，用于存储一个坐标点的`x`和`y`值，在本次的工程中，由于获取到的坐标是`Integer`整型，所以在`Shape`等类中大量使用了`Pair<Integer>`的类型，其中，`getX`和`getY`的方法可以分别得到`x`和`y`的值，为了方便也添加了`setNew`方法用于更新坐标。
此外，由于需要计算是否鼠标的位置在椭圆内/直线上，我们也需要一个计算距离的函数，因此在`Pair`类中也存在了`distance`方法，用于计算两点之间的距离。
```java
class Pair<T> implements Serializable{
    private T x;
    private T y;
    Pair(T _x, T _y){
        x = _x;
        y = _y;
    }

    public T getX(){
        return x;
    }

    public T getY() {
        return y;
    }

    public void setNew(T _x, T _y){
        x = _x;
        y = _y;
    }

    double distance(Pair<T> p){
        return Math.sqrt(Math.pow((int)p.getX() - (int)x, 2) + Math.pow((int)p.getY() - (int)y, 2));
    }
}
```

#### 图形类
`Shape`类是一个抽象类，它作为所有图形的父类，其中包含了所有图形共有的属性，包含有确定图形位置的p1和p2两个坐标点，以及确定图形颜色的color属性，和存在有`thickness`变量以用于保存绘制图形的粗细

```java
abstract class Shape implements Serializable{
    public Pair<Integer> p1 = new Pair<>(0, 0);
    public Pair<Integer> p2 = new Pair<>(0, 0);
    public Color color = Color.black;
    public double thickness = 1;

    public abstract void draw(Graphics g, Color c);

    public abstract boolean isInside(Pair<Integer> p);

    public abstract void move(Pair<Integer> p);

    public void resize(double ratio){
        Pair<Integer> center = new Pair<>((int)((p1.getX() + p2.getX()) / 2), (int)((p1.getY() + p2.getY()) / 2));
        Pair<Integer> new_p1 = new Pair<>((int)(center.getX() - (center.getX() - p1.getX()) * ratio), (int)(center.getY() - (center.getY() - p1.getY()) * ratio));
        Pair<Integer> new_p2 = new Pair<>((int)(center.getX() - (center.getX() - p2.getX()) * ratio), (int)(center.getY() - (center.getY() - p2.getY()) * ratio));
        p1 = new_p1;
        p2 = new_p2;
    }

    public void setP1(Pair<Integer> p1) {
        this.p1 = p1;
    }

    public void setP2(Pair<Integer> p2) {
        this.p2 = p2;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public Color getColor() {
        return color;
    }

    public double getThickness(){
        return thickness;
    }

    public Pair<Integer> getTopLeftPoint(){
        return new Pair<>(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
    }

    public Pair<Integer> getBottomRightPoint(){
        return new Pair<>(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(), p2.getY()));
    }

}
```
而具体实现`Shape`类的共有四种类型，分别为`Line`, `Circle`, `Rect`, `Words`。每个类中都重写了`draw`, `isInside`和`move`方法，用于实现不同图形的绘制、判断点是否在图形内和移动操作。
不同的图形在判断鼠标是否选中的时候判断方法不同。

对于`Line`类型，我们需要计算鼠标选择的点距离画出的线段的距离，可以利用向量的叉乘和向量的模长来计算，而对于椭圆来说，我们可以从椭圆的数学性质入手，计算鼠标选取点坐标和两个焦点的距离之和，如果这个距离和小于椭圆的长轴长，则代表它处于椭圆的内部。矩形和文字相对简单，我们只需要判断鼠标所选取的坐标和待选择图形的边界的关系即可。

需要注意的是，`draw`函数除了有一个`Graphics`类型的参数`g`以外，还需要有一个`Color`类型的参数，它可以改变绘制图形的颜色，因此我们可以绘制纯白色的（和底色相同）图形以实现图形的擦除。
```java
class Line extends Shape{

    public Line(Pair<Integer> start_point, Pair<Integer> end_point) {
        this.p1 = start_point;
        this.p2 = end_point;
    }

    @Override
    public void draw(Graphics g, Color c) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke((float)thickness));
        g.setColor(c);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    @Override
    public boolean isInside(Pair<Integer> p) {
        Pair<Integer> P1_P = new Pair<>(p.getX() - p1.getX(), p.getY() - p1.getY());
        Pair<Integer> P1_P2 = new Pair<>(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        double _dist = Math.abs(P1_P.getX() * P1_P2.getY() - P1_P.getY() * P1_P2.getX()) / p1.distance(p2);
        return (_dist < 5.0) && p.getX() < Math.max(p1.getX(), p2.getX()) && p.getX() > Math.min(p1.getX(), p2.getX());
    }

    @Override
    public void move(Pair<Integer> p) {
        p1.setNew(p1.getX() + p.getX(), p1.getY() + p.getY());
        p2.setNew(p2.getX() + p.getX(), p2.getY() + p.getY());
    }
}
```



#### 图形存储
所有的图形被存储在静态类内变量`Shapes`中，定义如下，它是一个用于承接`Shape`类型的`ArrayList`，但是由于`Shape`类型是抽象类，它只能用于承接`Shape`类型的子类，也即我们需要绘制的几种元素。我们所有的变量所有的元素被依次添加到了其中，由于`ArrayList`的内存空间不是连续的，因此这样的存储有利于我们对任一元素的删除和插入，我们也可以很方便地利用for循环进行遍历操作。

```java
public static ArrayList<Shape> shapes = new ArrayList<>();
```


## 功能实现

### 视图更新
为了避免视图不停刷新而造成的闪屏，如果我们不进行操作时，屏幕是不会自动刷新的，当有图形被拖动的时候，程序会先将原先的图线用白色底色绘制一遍，然后每当进入`mouseDragged`回调的时候都会将所有图形都绘制一遍

### 放置图形


### 尺寸变化
图形的尺寸改变利用到了键盘的监听器，我们可以利用`addKeyListener`来对键盘事件进行监听，一个`KeyAdapter`的实例可以用来实现键盘按下的回调。
在检测到键盘被按下事件发生后，需要根据按下键位的不同来对应不同的行为，45号和61号分别为`+`和`-`，用于修改绘制图形的尺寸，每次按下增大键时尺寸变为原先的1.1倍。44和46号分别为`<`和`>`键，用于设置绘制图形线条的粗细，每次按下时直接对字号进行加一减一的操作。

```java
new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        if(resizing != null){
            ArrayList<Integer> _resize_codes = new ArrayList<>(Arrays.asList(61, 45, 44, 46, 82));
            if (_resize_codes.contains(e.getKeyCode())){
                resizing.draw(jf.getGraphics(), Color.white);
            }
            if(e.getKeyCode() == 61){
                resizing.resize(1.1);
            }
            else if(e.getKeyCode() == 45){
                resizing.resize(0.9);
            }
            else if(e.getKeyCode() == 44){
                resizing.setThickness(resizing.getThickness() - 1);
            }
            else if(e.getKeyCode() == 46){
                resizing.setThickness(resizing.getThickness() + 1);
            }
            else if(e.getKeyCode() == 82){
                shapes.remove(resizing);
                resizing = null;

            }
            if (_resize_codes.contains(e.getKeyCode())){
                if(resizing != null){
                    resizing.draw(jf.getGraphics(), resizing.getColor());
                }
            }
        }
    }

}
```

#### 大小改变
图形的大小改变依靠了`resize`函数，它的作用是根据图形原先的两个定位点，将图形的中心固定住，通过改变两个定位点的位置以实现改变图形大小的功能
```java
public void resize(double ratio){
    Pair<Integer> center = new Pair<>((int)((p1.getX() + p2.getX()) / 2), (int)((p1.getY() + p2.getY()) / 2));
    Pair<Integer> new_p1 = new Pair<>((int)(center.getX() - (center.getX() - p1.getX()) * ratio), (int)(center.getY() - (center.getY() - p1.getY()) * ratio));
    Pair<Integer> new_p2 = new Pair<>((int)(center.getX() - (center.getX() - p2.getX()) * ratio), (int)(center.getY() - (center.getY() - p2.getY()) * ratio));
    p1 = new_p1;
    p2 = new_p2;
    }
```
#### 粗细改变
在`Shape`类中存在有图形线条粗细的变量`thickness`，每次绘制时会读取这个变量，检测到键盘操作时也是通过对这个变量的更改实现改变图线粗细的功能。图形绘制线条粗细的设定依靠了`setThickness`函数，其传入的参数是目标粗细的值。

### 删除图形
删除图形的操作在键盘操作时被检测，按下`R`键以实现图形的删除，当检测到有图形被选中并且按下`R`键的时候，`Shapes`中的对应元素会被删除，并重新绘制所有的界面。同时需要将当前选择元素的指针置为空，否则会出现野指针的错误。
```java
if(e.getKeyCode() == 82){
    shapes.remove(resizing);
    resizing = null;
}

```

### 文件存取
保存到文件和从文件中读取主要利用了序列化的特性，由于我们本次工程中所应用到的都是基本数据类型，因此只需要在`Pair`类和`Shape`类中加上`implement Serializable`接口的语句，而不必自行实现序列化的操作。
此外，我们需要给菜单栏中打开文件和保存文件的按钮设置`Listener`，当打开文件的`Listener`监听到点击操作时，需要获取文件路径，利用`ObjectInputStream`来获取从文件中读入的信息并将反序列化的结果存入到`Shapes`类型的数组中。
从文件中读取部分的核心代码如下，需要注意的是，在将文件中的数据读入到`Shapes`变量之前，需要先清除掉之前绘制的内容，在这里我们使用和屏幕底色相同的纯白画笔绘制一遍所有图形以实现，在擦除屏幕内容后再反序列化读入并重新绘制。
```java
try {
    FileInputStream fis = new FileInputStream(file);
    ObjectInputStream ois = new ObjectInputStream(fis);
    // clear the original board
    for(Shape s : shapes){
        s.draw(jf.getGraphics(), Color.white);
    }
    shapes = (ArrayList<Shape>) ois.readObject();
    ois.close();
    for(Shape s : shapes){
        s.draw(jf.getGraphics(), s.getColor());
    }

} catch (IOException | ClassNotFoundException ex) {
    ex.printStackTrace();
}
```
序列化存储部分的代码如下，由于我们已经将所有的图形信息存储在变量`Shapes`中，这使得我们的序列化更加方便，只需要调用`writeObject`写入到文件中即可。
```java
try {
    FileOutputStream fos = new FileOutputStream(file);
    ObjectOutputStream os = new ObjectOutputStream(fos);
    os.writeObject(shapes);
    os.close();
} catch (IOException ex) {
    ex.printStackTrace();
}
```

