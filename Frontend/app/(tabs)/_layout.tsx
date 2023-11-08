import { Tabs } from 'expo-router';
import { useTheme } from '@react-navigation/native';

import * as VectorIcons from '@expo/vector-icons';


type IconType = typeof VectorIcons[keyof typeof VectorIcons];

type TabBarIconProps = {
  Icon: IconType;
  name: IconType['name'];
  size?: number;
  color: string;
};

function TabBarIcon(props: TabBarIconProps) {
  const Icon = props.Icon;
  const iconProps = { ...props, Icon: undefined, size: props.size || 28 };

  return <Icon style={{ marginBottom: 0 }} {...iconProps} />;
}


export default function TabsLayout() {
  const { colors } = useTheme();

  return (
    <Tabs
      initialRouteName='index'
      screenOptions={({ route, navigation }) => {
        return {
          headerShown: false,
          tabBarActiveTintColor: colors.primary,
          //tabBarLabel: navigation.isFocused() ? route.name : ''
          tabBarShowLabel: false,
        };
    }}
    >

      <Tabs.Screen name="index" 
        options={{
          tabBarIcon: ({ color }) => <TabBarIcon Icon={VectorIcons.FontAwesome} name="home" color={color} />,
        }}
      />

      <Tabs.Screen name="explore"
        options={{
          tabBarIcon: ({ color }) => <TabBarIcon Icon={VectorIcons.MaterialIcons} name="location-on" color={color} />,
        }}
      />

      <Tabs.Screen name="pub_route"
        options={{
          tabBarIcon: ({ color }) => <TabBarIcon Icon={VectorIcons.Ionicons} name="navigate" color={color} />
        }}
      />

      <Tabs.Screen name="friends"
        options={{
          tabBarIcon: ({ color }) => <TabBarIcon Icon={VectorIcons.FontAwesome5} name="user-friends" size={24} color={color} />
        }}
      />

      <Tabs.Screen name="search"
        options={{
          tabBarIcon: ({ color }) => <TabBarIcon Icon={VectorIcons.FontAwesome} name="search" size={24} color={color} />
        }}
      />

    </Tabs>
  );
}