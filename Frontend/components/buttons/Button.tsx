import { Pressable } from "react-native";
import { StyleProp, ViewStyle } from "react-native";
import { PressableProps } from "react-native";


export type ButtonProps = {
  style?: StyleProp<ViewStyle>;
  pressedStyle?: StyleProp<ViewStyle>;
} & PressableProps;


export function Button({style, pressedStyle, ...props}: ButtonProps) {
  return (
    <Pressable
      style={({ pressed }) => [style, pressed && pressedStyle]}
      {...props}
    >
      {props.children}
    </Pressable>
  );
}