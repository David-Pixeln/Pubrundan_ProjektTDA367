import * as VectorIcons from '@expo/vector-icons';
import { StyleProp, ViewStyle } from 'react-native';


type IconType = typeof VectorIcons[keyof typeof VectorIcons];

export type IconProps = {
  iconType: IconType;
  name: IconType['name'];
  size?: number;
  color?: string;
  style?: StyleProp<ViewStyle>;
};

export function Icon({iconType: IconType, size=28, color='white', ...props}: IconProps) {
  const iconProps = { size, color, ...props};

  const defaultStyle = {
    marginBottom: 0,
  };

  return <IconType style={[defaultStyle, props.style]} {...iconProps} />;
}