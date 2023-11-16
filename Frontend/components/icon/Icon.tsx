import * as VectorIcons from '@expo/vector-icons';


type IconType = typeof VectorIcons[keyof typeof VectorIcons];

export type IconProps = {
  Icon: IconType;
  name: IconType['name'];
  size?: number;
  color?: string;
};

export function Icon(props: IconProps) {
  const Icon = props.Icon;
  const iconProps = { ...props, Icon: undefined, size: props.size || 28, color: props.color || 'white' };

  return <Icon style={{ marginBottom: 0 }} {...iconProps} />;
}