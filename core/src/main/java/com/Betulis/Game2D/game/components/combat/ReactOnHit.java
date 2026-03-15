package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReactOnHit extends Component {
    private final List<Consumer<HitContext>> reactions = new ArrayList<>();

    public void add(Consumer<HitContext> r) {
        reactions.add(r);
    }

    public void trigger(HitContext ctx) {
        reactions.forEach(r -> r.accept(ctx));
    }
}
